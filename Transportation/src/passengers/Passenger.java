
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package passengers;

import vehicles.*;
import interfaces.*;
import locations.Location;

/**
 * A class to perform actions on a Passenger object
 * 
 * @author Ali Kaan Biber
 */
public class Passenger implements ownCar,usePublicTransport{
	//Counter to keep track and give ID to each Passenger object
	static int count = 0;
	int ID;
	boolean hasDriversLicense;
	double cardBalance;
	public Car car;
	public Location currentLocation;
	
	/**
	 * Constructs a Passenger object who doesn't own a car
	 * 
	 * @param ID Distinguishing feature of each Passenger object
	 * @param hasDriversLicense whether or not a Passenger has a car
	 * @param l Passengers current location
	 */
	public Passenger(int ID, boolean hasDriversLicense,Location l) {
		
		this.ID=ID;
		this.hasDriversLicense=hasDriversLicense;
		this.currentLocation=l;	
		//Add this passenger to current and history lists of their current location which is (0,0)
		currentLocation.current.add(this);
		currentLocation.history.add(this);
		//implement the counter
		count++;
	}
	
	/**
	 * Constructs a Passenger object who owns a car
	 * 
	 * @param ID Distinguishing feature of each Passenger object
	 * @param l Passengers current location
	 * @param fuelConsumption Fuel consumption rate of Passenger's car
	 */
	public Passenger(int ID, Location l, double fuelConsumption) {
		//Call the other constructor with "true" value for license
		this(ID,true,l);
		//Call the Car constructor to create a Car object
		this.car = new Car(ID,fuelConsumption);
		
	}
	
	/**
	 * Refuels the Car object's fuel amount by given amount
	 * 
	 * @param amount amount to be refuelled
	 */
	public void refuel(double amount) {
		if(car!=null ) {			//Check if the Passenger has a car
		this.car.refuel(amount);
		}
		}
	
	
	/**
	 * Transports this Passenger to another Location by car if some conditions are met
	 * 
	 * @param l Passenger's current location
	 */
	public void drive(Location l) {
		
		//Has a car and a license to drive
		if( car!= null && hasDriversLicense) {
			
			
			double usedFuel = car.getFuelConsumption()*l.getDistance(currentLocation);
			//If car has enough fuel transportation takes place
			if(car.getFuelAmount() >= usedFuel ) {
			
			car.setFuelAmount(usedFuel);		//Set the remaining fuel
			l.incomingPassenger(this);			//Call appropriate methods for current and desired locations
			currentLocation.outgoingPassenger(this);
			currentLocation = l ;
			
			}
				
		}
	
	}
	
	/**
	 * Sets a new car to this Passenger 
	 * Also updates hasDriversLicense to true
	 * 
	 * @param fuelConsumption the rate of consumed fuel per km
	 */
	public void purchaseCar(double fuelConsumption) {
		
		hasDriversLicense=true;
		car = new Car(ID,fuelConsumption);		
	}
	
	/**
	 * Transports this Passenger to another Location by Public Transportation if some conditions are met
	 * 
	 * @param p PublicTransport object Passenger wishes to ride
	 * @param l Passenger's current location
	 */
	public void ride(PublicTransport p, Location l) {
		
		//Check if the arrival and departure are in range
		if( p.canRide(currentLocation,l) ) {
		double price;
		//Set the price based on PublicTransport type
		//Call the getPrice method from appropriate class
		if(p instanceof Train) {
			double distance = currentLocation.getDistance(l);
			
			price = ((Train) p).getPrice(this,distance);
		}
		else {		
			
			price = ((Bus) p).getPrice(this);
		}
	
		//Check if this Passenger has enough liras in travel card
		if(cardBalance>=price) {
			
			cardBalance-=price;						//Set the new card balance
			l.incomingPassenger(this);				//Call appropriate methods for current and desired locations
			currentLocation.outgoingPassenger(this);
			currentLocation = l;		
			}		
		}	
	}
	
	/**
	 * Refills this Passenger travel card by given amount
	 * 
	 * @param amount amount to be refilled
	 */
	public void refillCard(double amount) {
		if(amount>0) {
			cardBalance+=amount;
		}
	}
	
	/**
	 * Returns remaining amount in Passenger's travel card
	 * 
	 * @return cardBalance
	 */
	public double getCardBalance() {
		return cardBalance;
	}
	
	/**
	 * Overrides toString method for a Passenger object
	 */
	public String toString() {

			return "Passenger " + ID +": ";
			
	}
	
	/**
	 * Returns the static variable count
	 * 
	 * @return static variable count
	 */
	public static int getCount() {
		return Passenger.count;
	}
	
	/**
	 * Returns the ID of this Passenger
	 * 
	 * @return ID 
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Returns if Passenger has a car
	 * @return Car status
	 */
	public boolean hasCar() {
		return this.car==null;
	}
	
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

