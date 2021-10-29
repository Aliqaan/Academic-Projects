
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package passengers;

import locations.Location;

/**
 * A class to perform actions on a Discounted Passenger object
 * Extends to Passenger class
 * 
 * @author Ali Kaan Biber
 */
public class DiscountedPassenger extends Passenger{
	
	/**
	 * Constructs a DiscountedPassenger who doesn't own a car
	 * 
	 * @param ID Distinguishing feature of each Passenger object
	 * @param hasDriversLicense whether or not a Passenger has a car
	 * @param l Passengers current location
	 */
	public DiscountedPassenger(int ID, boolean hasDriversLicense, Location l) {
		//Call the superclass' constructor
		super(ID,hasDriversLicense,l);
	}
	
	/**
	 * Constructs a DiscountedPassenger who owns a car
	 * 
	 * @param ID Distinguishing feature of each Passenger object
	 * @param l Passengers current location
	 * @param fuelConsumption Fuel consumption rate of Passenger's car
	 */
	public DiscountedPassenger(int ID, Location l, double fuelConsumption) {
		//Call the superclass' constructor
		super(ID,l,fuelConsumption);
	}
	
	
	
}
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

