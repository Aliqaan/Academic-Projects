
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package elements;

/**
 * Defines a class and performs operations on Message objects
 * 
 * @author Ali Kaan Biber
 *
 */
public class Message{
	
	static int numOfMessages = 0;
	int id;
	String body;
	User sender;
	User receiver;
	int timeStampSent;
	int timeStampRead;
	int timeStampReceived;
	
	/**
	 * Creates a Message object.
	 * 
	 * @param sender	A User object that will send the Message
	 * @param receiver	A User object that will receive the Message
	 * @param body		Message content
	 * @param server	Server object in which Message objects are stored
	 * @param time		an int value to keep track of time
	 */
	public Message(User sender, User receiver, String body, Server server, int time){
		
		id=numOfMessages;
		this.sender=sender;
		this.receiver=receiver;
		this.body=body;
		setTimeStampSent(time);
		numOfMessages++;
		
	}
	
	/**
	 * Sets TimeStampSent field
	 * Called when a new Message is created
	 * 
	 * @param time	Value to be assigned
	 */
	public void setTimeStampSent(int time) {
		
		timeStampSent=time;
	}
	
	/**
	 * Sets TimeStampRead field
	 * Used when a Message is read
	 * 
	 * @param time	Value to be assigned
	 */
	public void setTimeStampRead(int time) {
		
		timeStampRead=time;
	}
	
	/**
	 * Sets TimeStampReceived field
	 * Used when receiving a Message from server
	 * 
	 * @param time	Value to be assigned
	 */
	public void setTimeStampReceived(int time) {
		
		timeStampReceived=time;
	}
	
	/**
	 * Returns the id of Message object
	 * 
	 * @return	id field
	 */
	public int getId() {
		
		return id;
	}
	
	/**
	 * Returns the content of a Message object
	 * 
	 * @return body field
	 */
	public String getBody() {
		
		return body;
	}
	
	/**
	 * Compares two Message object considering their length in content and returns an integer value depending on that comparison
	 * 
	 * @param o Other Message object to make the comparison
	 * @return	an integer value depending on comparison's result
	 */
	public int compareTo(Message o) {
		
		if (this.body.length() > o.body.length()) {
			return 1;
		}
		else if (this.body.length() < o.body.length()) {
			return -1;
		}
		else
			return 0;
		
		
	}
	
	/**
	 * Compares this Message object with another object to see if they are equivalent
	 * 
	 * @param o	Another object to decide if they are the same
	 * @return	a boolean value tells if they are the same or not
	 */
	public boolean equals(Object o) {
		
		if(o instanceof Message) {
			String other = ((Message) o).getBody();
			if ( other.equals(this.getBody()) ) {
				
				return true;
			}
		
		}
		return false;
		
	}
	
	/**
	 * Returns a String represantation of Message object
	 * 
	 * @return A String with necessary information
	 */
	public String toString() {
		
		return "\tFrom: " + sender.id + " to: " + receiver.id + "\n\tReceived: " + timeStampReceived + " Read: " +
		timeStampRead+"\n\t"+body ;
		
	}
	
	/**
	 * Returns the sender object of this message
	 * 
	 * @return sender field
	 */
	public User getSender() {
		return sender;
	}
	
	/**
	 * Returns the receiver object of this message
	 * 
	 * @return receiver field
	 */
	public User getReceiver() {
		return receiver;
	}
	
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

