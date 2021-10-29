
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package vehicles;

import passengers.*;

/**
 * Defines a class to create and perform actions on a Train object
 * Extends to PublicTransport Class
 * 
 * @author Ali Kaan Biber
 */
public class Train extends PublicTransport{
	
	/**
	 * Constructs a Bus object
	 * 
	 * @param ID Distinguishing feature of PublicTransport class
	 * @param x1 X location of the starting point of Vehicle's operation range
	 * @param y1 Y location of the starting point of Vehicle's operation range
	 * @param x2 X location of the ending point of Vehicle's operation range
	 * @param y2 Y location of the ending point of Vehicle's operation range
	 */
	public Train (int ID, double x1, double y1, double x2, double y2) {
		//Call the superclass' constructor
		super(ID, x1, y1, x2, y2);
	}
	
	/**
	 * Returns amount needed to take the Train to a certain location
	 * 
	 * @param p Passenger object who wants to ride
	 * @param distance Distance between Passenger's current location and desired location
	 * @return The price to take the ride depending on the type of Passenger object
	 */
	public double getPrice(Passenger p,double distance) {
		//Stops are located at every 15 km
		int nofStops = (int) Math.round( distance / 15 ) ;
		//When a StandardPassenger object wants to ride, travelling one stop costs 5 liras
		if(p instanceof StandardPassenger) {
			
			return 5.0 * nofStops;
		}
		//When a DiscountedPassenger object wants to ride, travelling becomes %20 cheaper
		else {
			return 5.0 * nofStops * (1-0.2) ;
		}
		
	}

}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

