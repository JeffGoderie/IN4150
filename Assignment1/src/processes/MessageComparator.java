package processes;

import java.util.Comparator;

import clocks.Message;

public class MessageComparator implements Comparator<Message>{
	public int compare(Message o1, Message o2) {
		if(o1.timestamp.smallerThan(o2.timestamp)){
			return -1;
		}
		else{
			return 1;
		}
	};
}
