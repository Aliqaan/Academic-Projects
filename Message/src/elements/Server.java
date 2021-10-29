
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package elements;

import java.io.*;
import java.util.*;

/**
 * Defines a class which performs operations on Message objects
 * 
 * @author Ali Kaan Biber
 */
public class Server{
	
	public long capacity;
	long currentSize;
	Queue<Message> msgs;
	public int msgDecider;		// A field to print a warning message
	
	/**
	 * Creates a new Server object
	 * 
	 * @param capacity	a long value that determines the maximum amount of characters a Server can store
	 */
	public Server(long capacity) {
		this.capacity=capacity;
		msgs = new LinkedList<Message>();
	}
	
	/**
	 * Prints a warning message depending on Server object's current load
	 * Uses msgDecider field to decide which message to print
	 * 
	 * @param printer	PrintStream object to print to a file
	 */
	public void checkServerLoad(PrintStream printer) {
		
		if(msgDecider==11) {
			printer.println("Warning! Server is 80% full.");
		}
		else if(msgDecider == 12) {
			printer.println("Warning! Server is 50% full.");
		}
		else if(msgDecider == 2) {
			printer.println("Warning! Server is 80% full.");
		}
		else if(msgDecider == 3) {
			printer.println("Warning! Server is 50% full.");
		}
		
		else if(msgDecider == -1) {
			printer.println("Server is full. Deleting all messages...");
			this.flush();
		}
		
		
		
		
		
	}
	
	/**
	 * Returns the current size of Server
	 * 
	 * @return currentSize field
	 */
	public long getCurrentSize() {
		
		return currentSize;
	}
	
	/**
	 * Sets the current size of Server
	 * 
	 * @param currentSize new value to assign
	 */
	public void setCurrentSize(long currentSize) {
		this.currentSize = currentSize;
	}
	
	/**
	 * Deletes all unreceived Messages from Server's queue
	 * Comes into play when Server is overloaded
	 * 
	 */
	public void flush() {
		
		msgs.clear();
		currentSize=0;
	}
	
	/**
	 * Returns Server Object's msgs Queue
	 * 
	 * @return a Queue of Message objects
	 */
	public Queue<Message> getQueue(){
		return msgs;
	}
	
	/**
	 * Compares Server's status before and after an operation and return an integer value depending on that result
	 * 
	 * @param rateBefore	Server's occupancy rate before operation
	 * @param rateAfter		Server's occupancy rat after operation
	 * @return	an integer value
	 */
	public int compareRates(double rateBefore, double rateAfter) {
		
		if( rateAfter < 50 ) {
			return 0;
		}
		else if(rateAfter >=100) {
			
		return -1;
			
		}
		
		else if( rateBefore<50 && rateAfter>=50 ) {
			
			if( rateAfter >= 80 ) {
			return 11;
			}
			
			else if( rateAfter < 80 ) {
				return 12;
			}
	
		}
		
		else if ( rateBefore > 50 && rateBefore<80 && rateAfter>=80 ) {
			return 2;
		}
		
		else if (rateBefore > 80 && rateAfter < 80 && rateAfter >= 50) {
			return 3;
		}
		
		else {
			return 0;
		}
		
		return 0;
		
	}
	
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

