package component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class CandidateComponent extends UnicastRemoteObject implements ComponentInterf{

	public ArrayList<ComponentInterf> untraversed;
	public ComponentInterf link;
	public int level;
	public int id;
	public boolean killed;
	public boolean elected;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5399380564644469896L;

	public CandidateComponent(int i) throws RemoteException {
		this.untraversed = null;
		this.link = null;
		this.level = 0;
		this.id = i;
		this.killed = false;
		this.elected = false;
	}

	@Override
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException {
		this.untraversed = new ArrayList<ComponentInterf>(Arrays.asList(c));
		this.untraversed.remove(this);
		System.out.println(this.id + " has been initialised.");
		sendNext();
	}

	@Override
	public void receive(int l, int i, ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(this.id + " has received message: Level " + l + ", ID" + i);
		if(this.id == i && !this.killed){
			this.level++;
			untraversed.remove(this.link);
			sendNext();
		}
		else{
			if (l < this.level || (l == this.level && i <= this.id)){ //Added <= instead of < to prevent endless looping, will have to ask TA about that
				System.out.println("Smaller");
			}
			else{
				System.out.println("Not Smaller");
				send(l, i, c);
				this.killed = true;
				//Goto R
			}
		}
	}

	@Override
	public void send(int l, int i, ComponentInterf c) throws RemoteException{
		c.receive(l, i, this);
		
	}
	
	public void sendNext() throws RemoteException{
		if(!untraversed.isEmpty()){
			this.link = untraversed.get((int)(Math.random()*untraversed.size()));
			//untraversed.remove(0);
			System.out.println(this.id + " sent message: Level " + this.level + ", ID " + this.id + " to " + (this.link).toString());
			send(this.level, this.id, this.link);
		}
		else{
			if(!this.killed){
				this.elected = true;
				System.out.println(this.id + " has been elected.");
			}
		}
	}

	public String toString(){
		return "" + this.id;
	}
}
