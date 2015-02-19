package clocks;

public class ScalarClock_Tests {
	public static void main (String[] args){
		ScalarClock sc1 = new ScalarClock(4,4);
		ScalarClock sc2 = new ScalarClock(3,4);
		System.out.println(sc1.smallerThan(sc2));
		System.out.println(sc1);
		Message<String> msg = new Message("hello", sc1);
		System.out.println(msg);
	}
}
