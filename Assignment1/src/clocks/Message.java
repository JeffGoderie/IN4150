package clocks;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7074103411662207182L;
	private String msg;
	public ScalarClock timestamp;
	
	public Message(String m, ScalarClock t){
		this.msg = m;
		this.timestamp = t;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return msg.toString();
	}
}
