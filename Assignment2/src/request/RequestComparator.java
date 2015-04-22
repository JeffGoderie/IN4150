package request;

import java.util.Comparator;

public class RequestComparator implements Comparator<Request>{
	public int compare(Request o1, Request o2) {
		if(o1.timestamp.smallerThan(o2.timestamp)){
			return -1;
		}
		else{
			return 1;
		}
	};
}
