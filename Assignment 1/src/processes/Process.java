package processes;
import clocks.*;

public class Process {
	MessageQueue msgQ;
	
	/*
	 * Remove message from queue
	 * Broadcast the message (to everyone)
	 * Acknowledge the message (to everyone)
	 * Check if N acknowledgements have been received for that message
	 * If so, remove message from queue
	 */
	
	public Process(){
		msgQ = new MessageQueue();
	}
	
	//broadcast a message
	public void broadCast(Message msg){
		
	}
	
	//broadcast an acknowledgment
	public void broadCastAck(Ack a){
		
	}
	
	public void acknowledge(){
		Ack a = new Ack();
	}
}
