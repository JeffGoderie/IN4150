package processes;

import clocks.Message;
import clocks.ScalarClock;

public class Message_Test {
	public static void main(String[] args){
		MessageQueue test = new MessageQueue();
		test.message_queue.add(new Message("test",new ScalarClock(4,4)));
		test.message_queue.add(new Message("test",new ScalarClock(4,1)));
		test.message_queue.add(new Message("test3", new ScalarClock(2,3)));
		test.message_queue.add(new Message("test2", new ScalarClock(3,4)));
		System.out.println(test.message_queue.poll().toString());
		System.out.println(test.message_queue.poll().toString());
		System.out.println(test.message_queue.poll().toString());
		System.out.println(test.message_queue.poll().toString());
	}
}
