
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package vehicles;

import locations.Location;

/**
 * Defines an abstract class to perform some operations on its child classes
 * 
 * @author Ali Kaan Biber
 */
public abstract class PublicTransport {
	//Counter to keep track and give ID to each PublicTransport object
	static int count = 0;
	int ID;
	double x1;
	double y1;
	double x2;
	double y2;
	
	/**
	 * Constructs a PublicTransport vehicle
	 * 
	 * @param ID Distinguishing feature of each PublicTransport object
	 * @param x1 X location of the starting point of Vehicle's operation range
	 * @param y1 Y location of the starting point of Vehicle's operation range
	 * @param x2 X location of the ending point of Vehicle's operation range
	 * @param y2 Y location of the ending point of Vehicle's operation range
	 */
	public PublicTransport(int ID, double x1, double y1,double x2, double y2) {
		
		this.ID=ID;
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		//Implement the counter
		count++;
	}
	
	/**
	 * Determines if the vehicle can be taken for ride by comparing its operation range with departure and arrival locations.
	 * 
	 * @param departure Location of Passenger at that moment
	 * @param arrival Desired location of Passenger to travel
	 * @return Whether or not vehicle can be taken
	 */
	public final boolean canRide(Location departure, Location arrival) {
		
		//X coordinate of departure point must be in range
		if( (departure.locationX<=x2 && departure.locationX >=x1 ) || (departure.locationX<=x1 && departure.locationX >=x2 ))  {
			//Y coordinate of departure point must be in range
			if( (departure.locationY<=y2 && departure.locationY >= y1 ) || (departure.locationY<=y1 && departure.locationY >= y2 )) {
				//X coordinate of arrival point must be in range
				if((arrival.locationX<=x2 && arrival.locationX >=x1 ) || (arrival.locationX<=x1 && arrival.locationX >=x2 )) {
					//Y coordinate of arrival point must be in range
					if((arrival.locationY<=y2 && arrival.locationY >= y1 ) || (arrival.locationY<=y1 && arrival.locationY >= y2 )) {
						return true;
					}		
				}
			}	
		}
		//If any of the statements above is false, this vehicle is not suitable for the ride
		return false;
		
	}
	
	/**
	 * Returns the static variable count
	 * 
	 * @return static variable count
	 */
	public static int getCount() {
		return PublicTransport.count;
	}
	
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE





