package utilities;

/**
 * Advance programming assigmment 1
 *
 * @version 1 18-08-2018
 * @author Chaoming Li (s3639056)
 *
 */

public class Apartment extends Property {
	/*
	 * constructor for apartment
	 *
	 * apartment is one type of property
	 *
	 */

	public Apartment(String propertyID, int streetNum, String streetName, String suburb, int bedroomNum) {
		super(propertyID, streetNum, streetName, suburb, bedroomNum, "Apartment");
		switch (bedroomNum) {
		case 1:
			setRentalRate(143);
			break;
		case 2:
			setRentalRate(210);
			break;
		case 3:
			setRentalRate(319);
			break;
		}
		super.setLateFeeRate(super.getRentalRate() * 1.15);
	}

}
