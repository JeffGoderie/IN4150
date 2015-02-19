package clocks;

public class Message{
	private String msg;
	public ScalarClock timestamp;
	
	public Message(String m, ScalarClock t){
		this.msg = m;
		this.timestamp = t;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Message: " + msg.toString() + "\n" + timestamp.toString() + "\n";
	}
}
