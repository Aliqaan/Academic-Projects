
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package vehicles;

import passengers.Passenger;
import passengers.StandardPassenger;

/**
 * Defines a class to create and perform actions on a Bus object
 * Extends to PublicTransport Class
 * 
 * @author Ali Kaan Biber
 */
public class Bus extends PublicTransport{
	
	/**
	 * Constructs a Bus object
	 * 
	 * @param ID Distinguishing feature of PublicTransport class
	 * @param x1 X location of the starting point of Vehicle's operation range
	 * @param y1 Y location of the starting point of Vehicle's operation range
	 * @param x2 X location of the ending point of Vehicle's operation range
	 * @param y2 Y location of the ending point of Vehicle's operation range
	 */
	public Bus (int ID, double x1, double y1, double x2, double y2) {
		//Call the superclass' constructor
		super(ID, x1, y1, x2, y2);
	}
	
	/**
	 * Returns amount needed to take the Bus
	 * 
	 * @param p Passenger object that wants to ride 
	 * @return The price depending on the type of Passenger object
	 */
	public double getPrice(Passenger p) {
		//Price for StandardPassenger is fixed at 2.0 liras
		if (p instanceof StandardPassenger) {
			return 2.0;
		}
		//%50 discount for DiscountedPassenger 
		else {
			return 1.0;
		}
		
	}
	
	
	
	
	
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

