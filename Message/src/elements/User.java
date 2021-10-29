
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package elements;

import boxes.*;
import java.util.*;

/**
 * Defines a class and performs operations on a User object
 * 
 * @author Ali Kaan Biber
 */
public class User{
	
	
	int id;
	Inbox inbox;
	Outbox outbox;
	ArrayList<User> friends;
	
	/**
	 * Constructs a User object
	 * 
	 * @param id	an integer value that represents ID
	 */
		public User(int id) {
		
		this.id=id;
		friends=new ArrayList<User>();
		inbox = new Inbox(this);
		outbox= new Outbox(this);
	
	}
	
		/**
		 * Makes two User objects friends so that they can send and receive messages from each other
		 * Adds them to friends ArrayList 
		 * 
		 * @param other	User object to become friends with
		 */
		public void addFriend(User other) {
			if( !this.isFriendsWith(other)) {
				
				friends.add(other);
				other.friends.add(this);	
			}	
		}
	
		/**
		 * Ends two User object's friendship
		 * Removes them from friends ArrayList
		 * 
		 * @param other	User object to remove from friend list
		 */
		public void removeFriend(User other) {
			
			if(this.isFriendsWith(other)) {
				
				friends.remove(other);
				other.friends.remove(this);
				
			}
		}
		
		/**
		 * Returns if two User objects are friends or not
		 * 
		 * @param other	Another User object 
		 * @return	true if they are already friends false otherwise
		 */
		public boolean isFriendsWith(User other) {
			
			if(friends.contains(other)) {
				return true;
			}
			
			return false;
			
		}
		
		/**
		 * Constructs and sends a Message object to another User object 
		 * 
		 * @param receiver	User object that will receive the message
		 * @param body		A string that represents the content of Message
		 * @param time		an integer value to keep track of operation time
		 * @param server	Server object to store unreceived Message objects
		 */
		public void sendMessage(User receiver, String body, int time, Server server) {
			
			Message m = new Message(this, receiver, body, server, time);
			this.outbox.getQueue().add(m);		
			
			server.msgs.add(m);
			int len = body.length();
		
			server.currentSize = server.getCurrentSize() + len;
				
		}
		
		/**
		 * Returns the inbox field of User object
		 * 
		 * @return inbox field
		 */
		public Inbox getInbox() {
			return inbox;
		}
		
		
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

