package utilities;

/**
 * Advance programming assigmment 1
 *
 * @version 1 18-08-2018
 * @author Chaoming Li (s3639056)
 *
 */
public abstract class Property {
	private String propertyID;
	private int streetNum;
	private String streetName;
	private String suburb;
	private int bedroomNum;
	private String type;
	private String status;
	private double rentalRate;
	private double lateFeeRate;
	final private Record[] recordList = new Record[10];
	private int recordNum;

	/**
	 * constructor for property
	 *
	 * @param propertyID
	 *            the property's ID
	 * @param streetNum
	 *            streetNum
	 * @param streetName
	 *            streetName
	 * @param suburb
	 *            streetName
	 * @param bedroomNum
	 *            number of bedroom
	 * @param type
	 *            property type
	 */
	public Property(String propertyID, int streetNum, String streetName, String suburb, int bedroomNum, String type) {
		this.propertyID = propertyID;
		this.streetNum = streetNum;
		this.streetName = streetName;
		this.suburb = suburb;
		this.bedroomNum = bedroomNum;
		this.type = type;
		this.status = "Available";
	}

	// getters
	public String getPropertyID() {
		return this.propertyID;
	}

	public int getStreetNum() {
		return this.streetNum;
	}

	public String getStreetName() {
		return this.streetName;
	}

	public String getSuburb() {
		return this.suburb;
	}

	public int getBedroomNum() {
		return this.bedroomNum;
	}

	public String getType() {
		return this.type;
	}

	public String getStatus() {
		return this.status;
	}

	public double getRentalRate() {
		return this.rentalRate;
	}

	public double getLateFeeRate() {
		return this.lateFeeRate;
	}

	// set
	public void setStatus(String status) {
		this.status = status;
	}

	public void setRentalRate(double rentalRate) {
		this.rentalRate = rentalRate;
	}

	public void setLateFeeRate(double lateFeeRate) {
		this.lateFeeRate = lateFeeRate;
	}

	public void addRecord(Record xx) {
		if (recordNum == 10) {
			recordList[9] = null;
		}
		for (int i = recordNum - 1; i > 0; i--) {
			recordList[i] = recordList[i - 1];
		}
		recordList[0] = xx;
		recordNum++;
	}

	/**
	 * rent method decides whether the property can be rented
	 *
	 * @param customerID
	 * @param rentDate
	 * @param numOfRentDay
	 * @return false means the property can not be rented
	 */
	public boolean rent(String customerID, DateTime rentDate, int numOfRentDay) {
		DateTime enday = new DateTime(rentDate, numOfRentDay);
		if (this.status.equals("Available")) {
			if (this.type.equals("Apartment")) {
				if (this.checkDate(rentDate, numOfRentDay)) {
					this.status = "rented";
					Record xx = new Record(this, customerID, rentDate, enday);
					this.addRecord(xx);
					System.out.println("successfully rent the property");
					return true;
				}
			} else if (this.type.equals("Premium Suit")) {
				if (this.checkPremiunSuitDate(rentDate, numOfRentDay)) {
					this.status = "rented";
					Record xx = new Record(this, customerID, rentDate, enday);
					this.addRecord(xx);
					System.out.println("successfully rent the property");
					return true;
				} else {
					return false;
				}
			}
		}
		System.out.println("this property is not aviliable right now");
		return false;
	}

	public boolean returnProperty(DateTime returnDate) {
		if (this.status.equals("rented")) {
			if (DateTime.diffDays(returnDate, this.recordList[0].getRentDate()) >= 0) {
				this.recordList[0].setActualRenturnDate(returnDate);
				this.status = "Available";
				this.recordList[0].setFee();
				System.out.println("the Property has successfully returned");
				return true;
			} else {
				System.out.println("the ruturn date should after than the rental day");
				return false;
			}
		}
		System.out.println("the Property is not rented");
		return false;
	}

	/**
	 * apartment should be checked number of the day rented base on week.
	 *
	 * @param customerID
	 * @param rentDate
	 * @param numOfRentDayt
	 * @return false means the property can not be rented
	 */
	public boolean checkDate(DateTime startday, int numOfRentDay) {
		String a = null;
		int b = startday.getWeek();
		switch (b) {
		case 1:
			a = "Sunday";
			break;
		case 2:
			a = "Monday";
			break;
		case 3:
			a = "Tuesday";
			break;
		case 4:
			a = "Wednesday";
			break;
		case 5:
			a = "Thursday";
			break;
		case 6:
			a = "Friday";
			break;
		case 7:
			a = "Satuday";
			break;
		}
		if (b >= 1 && b <= 5) {
			if (numOfRentDay < 2) {
				System.out.println("It's" + a + ", the minimum of rental day should be 2");
				return false;
			}
		} else {
			if (numOfRentDay < 3) {
				System.out.println("It's" + a + ", the minimum of rental day should be 3");
				return false;
			}
		}
		if (numOfRentDay > 28) {
			System.out.println("the maximum of rental day should be 28 and yours is" + numOfRentDay);
			return false;
		}
		return true;
	}

	/**
	 * PremiumSuit should be checked the rental period is not within the maintance
	 * date.
	 *
	 * @param customerID
	 * @param rentDate
	 * @param numOfRentDayt
	 * @return false means the property can not be rented
	 */
	public boolean checkPremiunSuitDate(DateTime rentDate, int numOfRentDay) {
		PremiumSuit ps = (PremiumSuit) this;
		int rentday = DateTime.diffDays(rentDate, ps.getLastMaintenanceDate());
		if (rentday >= 10) {
			DateTime lastDateforRent = new DateTime(ps.getLastMaintenanceDate(), 9);
			DateTime nextMaintenanceDate = new DateTime(ps.getLastMaintenanceDate(), 10);
			System.out.println("you can not rent the day after" + lastDateforRent.toString()
					+ ", because we need to maintenance on" + nextMaintenanceDate.toString());
			return false;
		}
		int maximumRentalDay = 10 - rentday;
		if (rentday > 0 && maximumRentalDay < numOfRentDay) {
			System.out.println("start from the date: " + rentDate.toString() + "\n you can not rent it over "
					+ maximumRentalDay + " days");
			return false;
		}
		return true;
	}

	// performMaintance method check the status of property and set it to the status
	// of Maintenance
	public boolean performMaintenance() {
		if (this.status.equals("Available")) {
			this.status = "Maintenance";
			System.out.println(this.propertyID + "is under maintenance now");
			return true;
		}
		System.out.println(this.propertyID + "can not be maintenance right now");
		return false;
	}

	/**
	 * completeMaintance method finish the maintenance and set the status to
	 * available and update the last maintance date.
	 *
	 *
	 */
	public boolean completeMaintance(DateTime completeMaintance) {
		if (this.status.equals("Maintenance")) {
			this.status = "Available";
			if (this instanceof PremiumSuit)
				((PremiumSuit) this).setLastMaintenceDate(completeMaintance);
			System.out.println(this.propertyID + " finish the Maintenance");
			return true;
		} else {
			System.out.println(this.propertyID + "is not under Maintenance");
			return false;
		}
	}

	// toString to get the rental detail
	public String toString() {
		String information = this.propertyID + ":" + this.streetNum + ":" + this.streetName + "+" + this.suburb + ":"
				+ this.type + ":" + this.bedroomNum + ":" + this.status;
		if (this.type.equals("Premium Suit"))
			information += ":" + ((PremiumSuit) this).getLastMaintenanceDate();
		return information;

	}

	public String getDetails() {
		String details = null;
		details = String.format("%-25s:%1s\n", "propertyID", this.propertyID)
				+ String.format("%-25s:%1s\n", "Address", this.streetNum + " " + this.streetName + " " + this.suburb)
				+ String.format("%-25s:%1s\n", "type", this.type)
				+ String.format("%-25s:%1s\n", "bedroom", this.bedroomNum)
				+ String.format("%-25s:%1s\n", "status", this.status);
		if (this.type.equals("Premium Suit")) {
			details += String.format("%-25s:%1s\n", "last Maintence", ((PremiumSuit) this).getLastMaintenanceDate());
		}
		if (this.recordList[0] != null) {
			details += "RENTAL RECORD\n";
			details += this.recordList[0].getDetails();
		} else if (this.recordList[0] == null) {
			details += String.format("%-25s:%1s\n", "RENTAL RECORD", "empty");
		}
		return details;
	}
}
