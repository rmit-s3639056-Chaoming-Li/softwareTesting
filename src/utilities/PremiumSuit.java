package utilities;

/**
 * Advance programming assigmment 1
 *
 * @version 1 18-08-2018
 * @author Chaoming Li (s3639056)
 *
 */

public class PremiumSuit extends Property {
	private DateTime lastMaintenanceDate;

	public PremiumSuit(String propertyID, int streetNum, String streetName, String suburb, DateTime lastMaintenceDate) {
		super(propertyID, streetNum, streetName, suburb, 3, "Premium Suit");
		setRentalRate(554);
		setLateFeeRate(662);
		this.lastMaintenanceDate = lastMaintenceDate;
	}

	// getter
	public DateTime getLastMaintenanceDate() {
		return this.lastMaintenanceDate;
	}

	/**
	 * set the last maintance date
	 *
	 * @param lastMaintanceDate
	 *            lat maintance date
	 */
	public void setLastMaintenceDate(DateTime lastmaintenceDate) {
		this.lastMaintenanceDate = lastmaintenceDate;
	}

}
