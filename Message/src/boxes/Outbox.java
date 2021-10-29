
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package boxes;

import elements.*;
import java.util.*;

/**
 * Defines an Outbox Class for User Objects
 * 
 * 
 * @author Ali Kaan Biber
 */
public class Outbox extends Box{
	
	Queue<Message> sent;
	
	/**
	 * Creates an Outbox Object
	 * Initializes the sent queue to store the sent messages
	 * 
	 * @param user	Owner of the Outbox
	 */
	public Outbox(User user) {
		super();
		owner=user;
		sent = new LinkedList<Message>();
	}
	
	/**
	 * Returns the sent Queue to be used in some other Class
	 *  
	 * @return sent field
	 */
	public Queue<Message> getQueue(){
		return sent;
	}
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

