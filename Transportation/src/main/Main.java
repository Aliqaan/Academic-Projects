
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package main;

import java.util.*;
import vehicles.*;
import java.io.*;
import passengers.*;
import locations.*;

/**
 * Defines a Main class to execute program
 * 
 * @author Ali Kaan Biber
 */
public class Main {
	
	/**
	 * Runs the program
	 * 
	 * @param args General main method argument
	 * @throws FileNotFoundException Exception handling
	 */
	public static void main(String[] args) throws FileNotFoundException {

		Scanner input = new Scanner(new File(args[0]));
		PrintStream output = new PrintStream(new File(args[1]));

		ArrayList<Passenger> passengers = new ArrayList<Passenger>();  	//Passenger ArrayList to reach Passenger objects
		ArrayList<Location> locations = new ArrayList<Location>();		//Location ArrayList to reach Location objects
		//PublicTransport ArrayList to reach PublicTransport objects
		ArrayList<PublicTransport> vehicles = new ArrayList<PublicTransport>();		
		
		
		
		  Location l = new Location(0, 0, 0); // The first location is always (0,0).
		  locations.add(l);
	
		int operations = input.nextInt(); // operation count
		
		for(int i=1; i<= operations; i++) {		//For loop to perform operations based on operation type in the input file
			
			int operationType = input.nextInt();	//operation type
			
			if( operationType == 1 ) {			//Creating a Passenger object
			
			String str = input.next();		//Type of Passenger "S" for Standard and "D" for Discounted 
			boolean license;			//Boolean value to state if Passenger has a car or not.Will be used in the constructor
			if(input.nextInt()==0) {		//license status
				license = false;
			}
			else {
				license = true;
			}
			
			if(input.nextInt()==0) {		//car status
				if(str.equals("D")) {		//Creating a DiscountedPassenger without a car and add it to the list
					passengers.add(new DiscountedPassenger(Passenger.getCount(),license,l));
				}
				else {						//Creating a StandardPassenger without a car and add it to the list
					passengers.add(new StandardPassenger(Passenger.getCount(),license,l));
				}
			}else {
			
				double fuelConsumption = input.nextDouble();		//an information about Passenger's car
				if(str.equals("D")) {		//Creating a DiscountedPassenger with a car and add it to the list
					passengers.add(new DiscountedPassenger(Passenger.getCount(),l,fuelConsumption));
			}
				else {						//Create a StandardPassenger with a car and add it to the list
					passengers.add(new StandardPassenger(Passenger.getCount(),l,fuelConsumption));
				}
			}
			
			}
			
			else if( operationType == 2 ) {		//Creating a Location object
				
				double x = input.nextDouble();		//Coordinates of location
				double y = input.nextDouble();
				
				locations.add(new Location(Location.getCount(),x,y));	//Create and add it to the list
				
			}
			
			else if( operationType == 3 ) {		//Creating a PublicTransport object
				
				int vehicleType = input.nextInt();		// 1 is for Bus and 2 for Train objects
				//next 4 lines are the operation range for the vehicle
				double x1 = input.nextDouble();			
				double y1 = input.nextDouble();			
				double x2 = input.nextDouble();
				double y2 = input.nextDouble();
				
				if(vehicleType==1) {		//Create a Bus object and add it to the list
					vehicles.add(new Bus(PublicTransport.getCount(),x1,y1,x2,y2));
				}
				else {						//Create a Train object and add it to the list
					vehicles.add(new Train(PublicTransport.getCount(),x1,y1,x2,y2));
				}
				
			}
			
			else if( operationType == 4 ) {		//Transportation
				
				int passengerID = input.nextInt();		//Determines the Passenger wishes to travel
				int locationID = input.nextInt();		//Passenger's desired location to arrive
				int vehicleType = input.nextInt();		//Which vehicle to use
				
				
				
				
				//If desired location and current location are the same or
				//Passenger doesn't exist. No action
				if(locations.get(locationID) == passengers.get(passengerID).currentLocation || passengerID >= Passenger.getCount()) {
					if(vehicleType != 3) {	
						//When travelling with Bus or Train an extra input is given. Make sure to get rid of it
						input.nextInt();
					}
					continue;
				}
				
				if(vehicleType == 3) {		//Means personal car
					if( !passengers.get(passengerID).hasCar()) {	//Checks if the passenger has a car
					
					passengers.get(passengerID).drive(locations.get(locationID));
					}
				}
				else {		//Means Bus or Train
					// 1 is for Bus and 2 is for Train
					int vehicleID = input.nextInt();
					
					//Check if the type of vehicle given by vehicleID is the same as type
					if( (vehicles.get(vehicleID) instanceof Bus && vehicleType == 1) || 
							(vehicles.get(vehicleID) instanceof Train && vehicleType == 2) ) {
						
						passengers.get(passengerID).ride(vehicles.get(vehicleID), locations.get(locationID));
							
					}
				}	
			}
			
			else if( operationType == 5 ) {		//Purchasing a car
				
				int passengerID = input.nextInt();				//Passenger to purchase
				double fuelConsumption = input.nextDouble();	//Fuel consumption of the car to be purchased
				if(passengerID <= Passenger.getCount()) {		//Check if passengerID is valid
				passengers.get(passengerID).purchaseCar(fuelConsumption);
				}
			}
			
			else if( operationType == 6 ) {		//Refueling a car
				
				int passengerID = input.nextInt();			//Passenger to refuel his/her car
				double fuelAmount = input.nextDouble();		//Fuel amount to be added
				if(passengerID <= Passenger.getCount()) {	//Check if passengerID is valid
				passengers.get(passengerID).refuel(fuelAmount);
				}
			}
			
			else if( operationType == 7 ) {		//Refilling a travel card
				
				int passengerID = input.nextInt();			//Passenger to refill card
				double amount = input.nextDouble();			//Amount to be added to the card
				if(passengerID <= Passenger.getCount()) {	//Check if passengerID is valid
				passengers.get(passengerID).refillCard(amount);
				}
			}
			
		}
		
		for(Location i: locations) {  //For loop to print all Location objects
		
			output.println(i.toString());
			
			if(i.current.size() != 0 ) {		
				//If location has at least 1 Passenger, Print all the passengers
				//But we need to print it in ascending order. So we need to sort the list first
				Passenger [] sortedArray = arraySort(i);	
						
					for(int a = 0; a<sortedArray.length; a++) {				
					//Print the amount in card if Passenger has no car
					if(sortedArray[a].car == null) {
						output.print(sortedArray[a].toString() + roundDouble(sortedArray[a].getCardBalance()));
								
					} 
					//Otherwise, print the fuel amount of car
					else {
						output.print(sortedArray[a].toString() + roundDouble(sortedArray[a].car.getFuelAmount()));
					
					}
					//Last statement will have no printline statement
					if( locations.get(locations.size()-1) != i || a != sortedArray.length-1 ) {
						output.println();
					}
					}
				}
				
			}
		}
		
	/**
	 * Returns a sorted array of type Passenger 
	 * 
	 * @param l Location object to sort its Arraylist named current 
	 * @return An array of Passengers
	 */
	public static Passenger[] arraySort(Location l) {
		
		Passenger [] arrayToSort = new Passenger[l.current.size()];	//Array to store passenger objects
		for(int j =0; j<l.current.size(); j++) {		//Assigning
			
			arrayToSort[j] = l.current.get(j);
		}
		
		for(int a = 0; a<arrayToSort.length-1; a++) {			//Sorting array in order of ascending ID
			for(int b = a+1;b<arrayToSort.length; b++ ) {
				if(arrayToSort[b].getID() < arrayToSort[a].getID()) {
					Passenger temp = arrayToSort[b];
					arrayToSort[b] = arrayToSort[a];
					arrayToSort[a] = temp;		
				}
			}
			
		}
		
		return arrayToSort;
	}
	
	
	/**
	 * Crops a given double value to 2 digits after decimal
	 * 
	 * @param x The double variable that will be cropped
	 * @return a String with 2 digits after decimal point
	 */
	public static String roundDouble(double x) {
		
		int temp = (int) (x*100);	  	//Typecasting
		double cropped = temp/100.0;	//Double with 1 or 2 digit(s) after decimal
		String str = cropped + "";		//Convert into a String
		//if String has 1 digit after decimal add a "0"
		if(str.length()  == str.indexOf(".") + 2) {
			str = str + "0";
		}
	return str;
	}
	
	
	}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

