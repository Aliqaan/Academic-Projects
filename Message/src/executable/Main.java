
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package executable;

import java.util.*;
import java.io.*;
import elements.*;

/**
 * Defines a Main class to execute the program
 * 
 * @author Ali Kaan Biber
 */
public class Main{
	
	/**
	 * Runs the code and makes necessary calls
	 * Creates a Scanner object and reads from the input
	 * Makes the calls to other classes to perform operations
	 * Uses a PrintStream object to print to an output file
	 * 
	 * 
	 * @param args	Will take input and output file names
	 * @throws FileNotFoundException	To handle exceptions
	 */
	public static void main(String [] args) throws FileNotFoundException {
		
		
		Scanner input = new Scanner(new File(args[0]));
		PrintStream printer = new PrintStream(new File(args[1]));
		
		int nofUsers = input.nextInt();
		int nofQueries = input.nextInt();
		int capacity = input.nextInt();
		input.nextLine();			//Proceed the next line
		
		User[] users = new User[nofUsers];		//Create an array of Usser Objects
		
		for(int i =0; i<nofUsers; i++) {		//Initialize User objects
			users[i] = new User(i);
		}
		
		Server server = new Server(capacity);	//Create a Server object
		
		
		int time =0;	//BUNU Öðren
		
		for(int i = 1; i <= nofQueries; i++) {	//For loop to scan and read all inputs and perform operations
			
			String line = input.nextLine();
			Scanner lineScan = new Scanner(line);	//Another Scanner object to read line by line
			
			int operationNo = lineScan.nextInt();
			
			if( operationNo == 0) {		//Send a message to a User
				
				int senderId = lineScan.nextInt();
				int receiverId = lineScan.nextInt();
				String msgBody = "";
				
				while(lineScan.hasNext()) {				
					msgBody += lineScan.next() + " " ;
				}
				msgBody=msgBody.substring(0,msgBody.length()-1);	
				
				//Storing this rate to send it as a parameter later
				double rateBefore = server.getCurrentSize() * 100 / server.capacity;
				
				//Check if the IDs are valid
				if(senderId<nofUsers && receiverId < nofUsers) {
				users[senderId].sendMessage(users[receiverId], msgBody, time, server);
				}
				
				// Storing this as well
				double rateAfter = server.getCurrentSize() * 100 / server.capacity;
				
				//Send the rates before and after the operation to print a warning message if necessary
				server.msgDecider = server.compareRates(rateBefore, rateAfter);
				server.checkServerLoad(printer);
				
				time++;	
			}
			
			else if(operationNo == 1) {			//Receiving Message objects from the Server
				
				int userId = lineScan.nextInt();
				
				double rateBefore = server.getCurrentSize() * 100 / server.capacity;
				
				//Check if the IDs are valid
				if(userId<nofUsers) {
				users[userId].getInbox().receiveMessages(server, time);
				}
				double rateAfter = server.getCurrentSize() * 100 / server.capacity;
				
				//Send the rates before and after the operation to print a warning message if necessary
				server.msgDecider = server.compareRates(rateBefore, rateAfter);
				server.checkServerLoad(printer);
				
				time++;
			}
			
			else if(operationNo == 2) {			//Read certain number of Messages
				
				int userId =lineScan.nextInt();
				int nofMessage = lineScan.nextInt();
				
				//Check if the IDs are valid
				if(userId<nofUsers) {
				 time = users[userId].getInbox().readMessages(nofMessage, time);
				}
				 
				
			}
			
			else if (operationNo == 21) {		//Read Messages from a certain User Object
				
				int userId = lineScan.nextInt();
				int senderId = lineScan.nextInt();
				
				//Check if the IDs are valid
				if(userId<nofUsers && senderId < nofUsers) {
				time = users[userId].getInbox().readMessages(users[senderId], time);
				}
				
			}
			
			else if(operationNo == 22) {		//Read the message with the given ID
				
				int userId = lineScan.nextInt();
				int msgId = lineScan.nextInt();
				
				//Check if the IDs are valid
				if(userId<nofUsers) {
				users[userId].getInbox().readMessage(msgId, time);
				}
				
				time++;
			}
			
			else if (operationNo == 3) {		//Add two User Object to their friend list
				
				int id1 = lineScan.nextInt();
				int id2 = lineScan.nextInt();
				
				//Check if the IDs are valid
				if(id1<nofUsers && id2 < nofUsers) {
				users[id1].addFriend(users[id2]);
				}
				
				time++;
			}
			
			else if (operationNo == 4) {		//Remove two User Object from their friend list
				
				int id1 = lineScan.nextInt();
				int id2 = lineScan.nextInt();
				
				//Check if the IDs are valid
				if(id1<nofUsers && id2<nofUsers) {
				users[id1].removeFriend(users[id2]);
				}
				
				time++;
			}
			
			else if (operationNo == 5) {		//Delete all Message Objects stored in the Server
				
				server.flush();
				time++;
			}
			
			else if (operationNo == 6) {		//Print current load of the Server
				
				printer.println("Current load of the server is " + server.getCurrentSize()  + " characters.");
				time++;
			}
			
			else if (operationNo == 61) {		//Print the last Message a User read
				
				int userId = lineScan.nextInt();
				Message last = null;
				
				//Check if the IDs are valid
				if(userId<nofUsers) {
				Iterator<Message> itr = users[userId].getInbox().getQueue().iterator();
				
				while(itr.hasNext()) {
					last=itr.next();
				}
				}
				
				if(last!=null) {
				printer.println(last.toString());
				}
				time++;	
			}
			
			
		}	

		
		
		
	}
	
	

}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

