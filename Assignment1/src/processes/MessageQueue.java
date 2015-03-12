package processes;
import java.util.PriorityQueue;
import java.io.*;
import clocks.*;

public class MessageQueue {
	public PriorityQueue<Message> message_queue;
	
	public MessageQueue(){
		message_queue = new PriorityQueue<Message>(100, new MessageComparator());
	}
	
	public Message peek(){
		return message_queue.peek();
	}
	
	public Message poll(){
		return message_queue.poll();
	}
	
	public void add(Message msg){
		message_queue.add(msg);
	}
}
