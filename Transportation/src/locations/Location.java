
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package locations;

import java.util.*;
import passengers.Passenger;


/**
 * Defines a class that performs operations on the location of Passenger objects
 * 
 * 
 * @author Ali Kaan Biber
 *
 */
public class Location{
	
	//static counter to keep track of # objects created and to give IDs
	static int count = 0;
	int ID;
	public double locationX;
	public double locationY;
	//ArrayLists to store Passenger objects
	public ArrayList <Passenger> history;
	public ArrayList <Passenger> current;
	
	/**
	 * Constructs a location object
	 * 
	 * @param ID location ID
	 * @param locationX this is the x coordinate
	 * @param locationY this is the y coordinate
	 */
	public Location(int ID, double locationX, double locationY) { //
		
		this.ID = ID;
		this.locationX = locationX;
		this.locationY = locationY;
		//All passengers who travels to this location
		history= new ArrayList<Passenger>();
		//Passengers who are currently at this location
		current = new ArrayList<Passenger>();
		//implement counter
		count++;
	}
	
	
	/**
	 * Calculates and returns the distance between two location objects
	 * 
	 * @param other Another location object
	 * @return The distance
	 */
	public double getDistance(Location other) {
		
		return Math.sqrt(Math.pow(this.locationX - other.locationX,2) + Math.pow(this.locationY - other.locationY,2));
		
	}
	
	/**
	 * Performs necessary actions when a passenger comes
	 * 
	 * @param p Passenger object that travels to this location
	 */
	public void incomingPassenger(Passenger p) {
		
		//If passenger travelled to this location before, no need to add to the list again.
		if( !history.contains(p)) {
			history.add(p);
		}	
		//Adding the passenger to the current passengers of this location
		current.add(p);
	}
	
	/**
	 * Performs necessary actions when a passenger leaves
	 * 
	 * @param p Passenger object that leaves this location
	 */
	public void outgoingPassenger(Passenger p) {
		//Remove the passenger from the current passenger list
		current.remove(p);
	}
	
	/**
	 * Overrides toString method and makes it more useful when needed
	 */
	public String toString() {
		return "Location " + ID +": (" + String.format("%.2f, %.2f",locationX, locationY) + ")" ;  
	
	}
	
	/**
	 *  Returns the static variable count
	 *  
	 * @return static variable count
	 */
	public static int getCount() {
		return Location.count;
	}
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

