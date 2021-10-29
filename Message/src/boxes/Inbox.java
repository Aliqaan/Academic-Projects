
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package boxes;

import elements.*;
import java.util.*;

/**
 * Operates as an inbox for a User object
 * Stores both unread and read messages
 * Child class of Box
 * 
 * @author Ali Kaan Biber
 */
public class Inbox extends Box{
	
	Stack<Message> unread;
	Queue<Message> read;
	
	/**
	 * Constructs an Inbox object
	 * Initializes the unread stack and read queue to store Message objects
	 * 
	 * @param user User Object
	 */
	public Inbox(User user) {
		super();
		owner=user;
		unread = new Stack<Message>();
		read = new LinkedList<Message>();
	}
	
	/**
	 * Receives the Message Objects send to User through Server
	 * Iterates through the msgs queue receives all messages send to User 
	 * Transfers messages from temporary queue to server queue in the end
	 * 
	 * 
	 * @param server	Server object which stores all the unreceived Message objects
	 * @param time	an integer value to keep track of operation time
	 */
	public void receiveMessages(Server server, int time) {

		//Creates a temporary queue to store Messages while iterating the msgs queue of Server object
		Queue<Message> temp = new LinkedList<Message>();
		
		while(!server.getQueue().isEmpty()) {
			
			Message m = server.getQueue().poll();
			if(m.getReceiver() == owner) {
				
				if(owner.isFriendsWith(m.getSender())) {
					
					m.setTimeStampReceived(time);
					unread.push(m);
					
					server.setCurrentSize(server.getCurrentSize() - m.getBody().length());
			
					continue;
				}
			}
			
			temp.add(m);
	
		}
		
		while(!temp.isEmpty()) {
			Message m = temp.poll();
			server.getQueue().add(m);
		}	
		
		
	}
	
	/**
	 * Reads Message Object in the unread Stack
	 * Only makes this operation num times
	 * Starts from the tail of the stack 
	 * 
	 * @param num	Decides how many messages will be read
	 * @param time	an integer value to keep track of time
	 * @return	number of messages read in other words time value for next operation
	 */
	public int readMessages(int num,int time) {

		int size = unread.size();
		if(size==0) {
			return time+1;
		}
		
		
		if(num==0 || size <= num) {
			while( !unread.isEmpty() ) {
				
				Message m = unread.pop();
				m.setTimeStampRead(time);
				read.add(m);
				time++;    				//BUNDAN EMÝN OL
				}		
		}
		else {
			
			int readMessage = 0;
			while( readMessage < num ) {
				
				Message m = unread.pop();
				m.setTimeStampRead(time);
				read.add(m);
				time++;
				readMessage++;
				
			}
			
		}
		return time;
		
		
		
	}
	
	/**
	 * Reads Message Object in the unread Stack
	 * Only reads if the Message send by particular sender
	 * Iterates through the unread stack
	 * Stores Message objects in a temporary stack if sender is different otherwise reads it.
	 * 
	 * @param sender	User object to reads messages from
	 * @param time		an integer value to keep track of operation time
	 * @return	number of messages read in other words time value for next operation
	 */
	public int readMessages(User sender, int time) {
		
		Stack<Message> temp = new Stack<Message>();
		boolean doesExist = false;
		while(!unread.isEmpty()) {
			
			Message m = unread.pop();
			if(m.getSender() == sender) {
				doesExist=true;
				read.add(m);
				m.setTimeStampRead(time);
				time++;
			}
			else {
				temp.push(m);	
			}		
		}
		
		while(!temp.isEmpty()) {
			Message m = temp.pop();
			unread.push(m);
		}
		 
		if( !doesExist ) {
			return time+1;
		}
		
		
		return time;
	}
	
	/**
	 * Reads Message Object in the unread Stack
	 * Reads a particular message with given ID as a parameter
	 * 
	 * @param msgId		Message ID to be read
	 * @param time		an integer value to keep track of time
	 */
	public void readMessage(int msgId, int time) {
		
		Stack<Message> temp = new Stack<Message>();
		
		while( !unread.isEmpty() ) {
			
			Message m = unread.pop();
			if( msgId == m.getId() ) {
				read.add(m);
				m.setTimeStampRead(time);
				break;
			}
			else {
				temp.push(m);
			}
		
		}
		
		while(!temp.isEmpty()) {
			 Message m = temp.pop();
			 unread.push(m);
		}
	
	}
	
	/**
	 * Returns the unread stack to use it in another class
	 * 
	 * @return unread field
	 */
	public Stack<Message> getStack(){
		return unread;
		
	}
	
	/**
	 * Returns the read queue to use it in another class
	 * 
	 * @return read field
	 */
	public Queue<Message> getQueue(){
		return read;
	}
	
	
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

