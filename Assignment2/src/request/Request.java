package request;

import java.io.Serializable;

import clocks.ScalarClock;

public class Request implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7074103411662207182L;
	public ScalarClock timestamp;
	
	public Request(ScalarClock t){
		this.timestamp = t;
	}
}
