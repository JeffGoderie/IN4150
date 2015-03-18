package request;
import java.util.PriorityQueue;

public class RequestQueue {
	public PriorityQueue<Request> message_queue;
	
	public RequestQueue(){
		message_queue = new PriorityQueue<Request>(100, new RequestComparator());
	}
	
	public Request peek(){
		return message_queue.peek();
	}
	
	public Request poll(){
		return message_queue.poll();
	}
	
	public void add(Request msg){
		message_queue.add(msg);
	}
	
	public void remove(Request msg){
		message_queue.remove(msg);
	}
}
