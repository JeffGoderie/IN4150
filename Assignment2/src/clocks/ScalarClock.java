package clocks;

import java.io.Serializable;

public class ScalarClock implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2632119310190982463L;
	int Time;
	int ProcessId;
	
	public ScalarClock(int t, int id){
		Time = t;
		ProcessId = id;
	}
	
	public boolean smallerThan(ScalarClock sc2){
		if(sc2==null){
			return true;
		}
		else if (this.Time < sc2.Time){
			return true;
		}
		else if (this.Time == sc2.Time && this.ProcessId <= sc2.ProcessId){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int getId(){
		return ProcessId;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Time: " + Time + "\t Process: " + ProcessId;
	}
}
