package utilities;

/**
 * Advance programming assigmment 1
 *
 * @version 1 18-08-2018
 * @author Chaoming Li (s3639056)
 *
 */

public class Record {
	private String recordID;
	private DateTime rentDate;
	private DateTime estimatedReturnDate;
	private DateTime actualReturnDate;
	private double rentalFee;
	private double lateFee;
	private Property property;
	private String customerID;

	// getter
	public String getRecordID() {
		return this.recordID;
	}

	public DateTime getRentDate() {
		return this.rentDate;
	}

	public DateTime getEstimatedReturnDate() {
		return this.estimatedReturnDate;
	}

	public DateTime getActualReturnDate() {
		return this.actualReturnDate;
	}

	public double getRentalFee() {
		return this.rentalFee;
	}

	public double getLateFee() {
		return this.lateFee;
	}

	public Property getProperty() {
		return this.property;
	}

	public String getCustomerID() {
		return this.customerID;
	}

	/**
	 * constructor of record
	 * 
	 * @param property
	 * @param customerID
	 * @param rentDate
	 * @param estimatedReturnDate
	 */
	public Record(Property property, String customerID, DateTime rentDate, DateTime estimatedReturnDate) {
		this.customerID = customerID;
		this.property = property;
		this.recordID = this.property.getPropertyID() + "_" + this.customerID + "_" + rentDate.toString();
		this.rentDate = rentDate;
		this.estimatedReturnDate = estimatedReturnDate;
		this.recordID = property.getPropertyID() + "_" + this.customerID + "_" + this.rentDate.getEightDigitDate();
	}

	// set
	public void setActualRenturnDate(DateTime actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}
	
	public void setFee() {
		if (DateTime.diffDays(actualReturnDate, estimatedReturnDate) <= 0) {
			this.rentalFee = DateTime.diffDays(actualReturnDate, rentDate) * this.property.getRentalRate();
		} else {
			this.rentalFee = DateTime.diffDays(estimatedReturnDate, rentDate) * this.property.getRentalRate();
			this.lateFee = DateTime.diffDays(actualReturnDate, estimatedReturnDate) * this.property.getLateFeeRate();
		}
	}

	// overide the method toString
	public String toString() {
		String information = null;
		if (this.property.getStatus().equals("Available")) {
			information = this.recordID + ":" + this.rentDate.toString() + ":" + this.estimatedReturnDate.toString()
					+ ":" + this.actualReturnDate.toString() + ":" + this.rentalFee + "+" + this.lateFee;
		} else {
			information = this.recordID + ":" + this.rentDate.toString() + ":" + this.estimatedReturnDate.toString()
					+ ":none :none :none";
		}
		return information;
	}

	// getDetails method get the detail of the record
	public String getDetails() {
		String details;
		details = String.format("%-25s:%1s\n", "Record ID", this.recordID)
				+ String.format("%-25s:%1s\n", "rent date", this.rentDate.toString())
				+ String.format("%-25s:%1s\n", "EstimatedReturnDate", this.estimatedReturnDate.toString());
		if (this.actualReturnDate != null) {
			details += String.format("%-25s:%1s\n", "actualReturnDate", this.actualReturnDate)
					+ String.format("%-25s:%1s\n", "rentalFee", this.rentalFee)
					+ String.format("%-25s:%1s\n", "lateFee", this.lateFee);
		}
		details += "--------------------------------------------";
		return details;
	}

}
