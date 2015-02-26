package clocks;

public class ScalarClock {
	int Time;
	int ProcessId;
	
	public ScalarClock(int t, int id){
		Time = t;
		ProcessId = id;
	}
	
	public boolean smallerThan(ScalarClock sc2){
		if (this.Time < sc2.Time){
			return true;
		}
		else if (this.Time == sc2.Time && this.ProcessId < sc2.ProcessId){
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Time: " + Time + "\t Process: " + ProcessId;
	}
}
