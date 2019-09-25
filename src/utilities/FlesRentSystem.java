package utilities;

/**
*Advance programming assigmment 1
*
*@version 1 18-08-2018
*@author Chaoming Li (s3639056)
*
*/
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlesRentSystem {
	private Property[] propertyStore;
	private int propertyNum;

	// constructor for FlesRentSystem object
	public FlesRentSystem() {
		this.propertyStore = new Property[50];
		this.propertyNum = 0;		
	}

	// menu method show the menu
	public void MENU() {
		String[] menu = { "******** flesrentsystem***********", "add Property:\t\t 1", "rent property\t\t 2",
				"return property:\t\t 3", "property Maintenance\t\t 4", "Complete Maintenance\t\t 5",
				"Display all property\t\t 6", "Exit program:\t\t 7", "enter you choice" };
		int a = 0;
		do {
			for (int i = 0; i < menu.length; i++) {
				System.out.println(menu[i]);
			}
			
			a = inputInt();
			
			switch (a) {
			case 1:
				this.addProperty();
				break;
			case 2:
				this.rentProperty();
				break;
			case 3:
				this.returnProperty();
				break;
			case 4:
				this.propertyMaintenance();
				break;
			case 5:
				this.completeMaintenance();
				break;
			case 6:
				this.displayAllProperties();
				break;
			case 7:
				
			default:
				System.out.println("please enter an interger between 1 to 7");
				break;
			}
		} while (a != 7);
	}

	private void addProperty() {
		Property ps;
		if (this.propertyNum == 50) {
			System.out.println("number of property reach the maxmium 50");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.println("input the street Num");
		int streetNum = inputInt();
		
		if (streetNum == 0)return;
		
		System.out.println("input the street name");
		String StreetName = sc.nextLine();
		System.out.println("intput the suburb");
		String suburb = sc.nextLine();
		System.out.println("Enter the property type: 1 for Aparmenty, 2 for Premium Suit");
		int type = inputInt();
		
		if (type != 1 && type != 2) {
			System.out.println("please enter 1 or 2");
			return;
		}
		String propertyID = null;
		if (type == 1)
			propertyID = "A_" + streetNum + StreetName + suburb;
		else if (type == 2)
			propertyID = "S_" + streetNum + StreetName + suburb;
		System.out.println("property ID:" + propertyID);
		this.checkPropertyID(propertyID);

		if (type == 1) {
			System.out.println("enter bedroom number");
			int bedroomNum = inputInt();
			if (bedroomNum != 1 && bedroomNum != 2 && bedroomNum != 3) {
				System.out.println("the number of bedroom should be integer within 1 to 3");
				return;
			}
			ps = new Apartment(propertyID, streetNum, StreetName, suburb, bedroomNum);
		} else {
			System.out.println("enter maintancedate (dd/mm/yyyy)");
			DateTime lastMaintanceDate = this.checkDateformat(sc.nextLine());
			if (lastMaintanceDate == null)
				return;
			ps = new PremiumSuit(propertyID, streetNum, StreetName, suburb, lastMaintanceDate);
		}
		this.propertyStore[propertyNum] = ps;
		this.propertyNum += 1;
		System.out.println("successful");
		return;
	}

	// method for rent property
	public void rentProperty() {
		Scanner sc = new Scanner(System.in);
		System.out.println("input the property you want to rent");
		String propertyID = sc.nextLine();
		int rentPropertyNum = this.matchPropertyID(propertyID);
		if (rentPropertyNum == 100) {
			return;
		}
		if (this.propertyStore[rentPropertyNum].getStatus().equals("Available") == false) {
			System.out.println("the property is not availabe right now");
			return;
		}
		System.out.println("input customer ID");
		String customerID = sc.nextLine();
		System.out.println("rent date (dd/mm/yyyy)");
		DateTime rentDate = this.checkDateformat(sc.nextLine());
		if (rentDate == null)
			return;
		System.out.println("How mant day you want to rent");
		int numOfRentDay = inputInt();
		if (numOfRentDay == 0)
			return;
		this.propertyStore[rentPropertyNum].rent(customerID, rentDate, numOfRentDay);

		return;
	}

	// method for return property
	public void returnProperty() {
		Scanner sc = new Scanner(System.in);
		System.out.println("input the property you want to return");
		String propertyID = sc.nextLine();
		int rentPropertyNum = this.matchPropertyID(propertyID);
		if (rentPropertyNum == 100) {
			return;
		}
		String rd = sc.nextLine();
		System.out.println("the date you return the property(dd/mm/yyyy)");
		DateTime returndate = this.checkDateformat(sc.nextLine());
		if (returndate == null)
			return;
		this.propertyStore[rentPropertyNum].returnProperty(returndate);
	}

	// propertyMaintenance method to set the property to propertyMaintenance
	public void propertyMaintenance() {
		Scanner sc = new Scanner(System.in);
		System.out.println("input the property you want to Maintenance");
		String propertyID = sc.nextLine();
		int rentPropertyNum = this.matchPropertyID(propertyID);
		if (rentPropertyNum == 100)
			return;
		this.propertyStore[rentPropertyNum].performMaintenance();

	}

	// set the Maintenance property to complete
	public void completeMaintenance() {
		Scanner sc = new Scanner(System.in);
		System.out.println("input the property you want to complete maintenance");
		String propertyID = sc.nextLine();
		int rentPropertyNum = this.matchPropertyID(propertyID);
		if (rentPropertyNum == 100) {
			return;
		}
		System.out.println("the date of Complete maintance(dd/mm/yyyy)");
		DateTime date = this.checkDateformat(sc.nextLine());
		if (date == null)
			return;
		this.propertyStore[rentPropertyNum].completeMaintance(date);

	}

	// displayAllProperties method can show all the property
	private void displayAllProperties() {
		for (int i = 0; i < this.propertyNum; i++) {
			if (this.propertyStore[i] == null)
				break;
			System.out.println(this.propertyStore[i].getDetails());

		}
	}

	// check input Date format then call setDatetime method;
	public DateTime checkDateformat(String string) {
		Pattern pattern;
		pattern = Pattern.compile("^(0[1-9]||[1-2]\\d||3[01])/(0\\d||1[1-2])/[1-2]\\d{3}$");
		Matcher m = pattern.matcher(string);
		if (m.find() == false) {
			System.out.println("please input the format date");
			return null;
		}
		int year = Integer.parseInt(string.substring(6, 10));
		int month = Integer.parseInt(string.substring(4, 5));
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			if (month == 2) {
				pattern = Pattern.compile("^(0[1-9]||[12]\\d)/02/[12]\\d{3}$");
			}
		} else {
			if (month == 2) {
				pattern = Pattern.compile("^(0[1-9]||1\\d||2[1-8])/02/[12]\\d{3}$");
			}
		}
		if (m.find()) {
			return this.setDatetime(string);
		}
		System.out.println("please input the format date");
		return null;
	}

	// input the dd/mm/yyyy and set the DateTime
	public DateTime setDatetime(String string) {
		String rd = string;
		int day = Integer.parseInt(rd.substring(0, 2));
		int month = Integer.parseInt(rd.substring(3, 5));
		int year = Integer.parseInt(rd.substring(6, 10));
		DateTime setDate = new DateTime(day, month, year);
		return setDate;
	}

	// check the added propertyID has in the database or not
	public void checkPropertyID(String string) {
		for (int i = 0; i < this.propertyNum; i++) {
			if (propertyStore[i] == null)
				break;
			if (string.equals(propertyStore[i].getPropertyID())) {
				System.out.println("already have this property in the store");
				return;
			}
		}
	}

	// match input property ID
	public int matchPropertyID(String propertyID) {
		int rentPropertyNum = 0;
		for (int i = 0; i < this.propertyNum; i++) {
			if (this.propertyStore[i] == null)
				break;
			if (this.propertyStore[i].getPropertyID().equals(propertyID)) {
				rentPropertyNum = i;
				return rentPropertyNum;
			}
		}
		System.out.println("propertyID is not correct");
		return 100;
	}

	// input int and chect format and return int
	public int inputInt() {
		Scanner sc = new Scanner(System.in);
		if (sc.hasNextInt()) {
			return sc.nextInt();
		}
		System.out.println("please input integer");
		return 0;
	}
}
