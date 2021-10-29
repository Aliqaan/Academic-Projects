
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package vehicles;

/**
 * Defines a class to perform actions on a Car object
 * 
 * @author Ali Kaan Biber
 */
public class Car{
	private int ownerID;
	private double fuelAmount;
	private double fuelConsumption;
	
	/**
	 * Constructs a Car object
	 * 
	 * @param ID ID of the Passenger object who has that car
	 * @param fuelConsumption Rate of fuel consumption of that car
	 */
	public Car(int ID, double fuelConsumption) {
		
		this.ownerID=ID;
		this.fuelConsumption=fuelConsumption;
		
	}
	
	/**
	 * Refuels the fuel of that Car object
	 * 
	 * @param amount the amount will be added to car's current fuel
	 */
	public void refuel(double amount) {
		//Chech if the input is valid
		if(amount>0) {
		fuelAmount +=amount;
		}
		}
	
	/**
	 * Returns the fuel consumption rate
	 * 
	 * @return fuelConsumption
	 */
	public double getFuelConsumption() {
		return fuelConsumption;
	}
	
	/**
	 * Sets Car object's fuel amount by subtracting the amount used to travel
	 * 
	 * @param amount Amount used to travel to another location
	 */
	public void setFuelAmount(double amount) {
		
		fuelAmount = fuelAmount - amount;
	}
	
	/**
	 * Returns Car's current fuel amount
	 * 
	 * @return fuelAmount
	 */
	public double getFuelAmount() {
		return fuelAmount;
	}
	
	
}
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

