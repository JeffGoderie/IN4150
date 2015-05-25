package component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

import main.Server;

public class CandidateComponent extends UnicastRemoteObject implements ComponentInterf{

	public ArrayList<ComponentInterf> untraversed;
	public ComponentInterf link;
	public ComponentInterf old_link;
	public int level;
	public int id;
	public boolean killed;
	public boolean elected;
	public Server server;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5399380564644469896L;

	public CandidateComponent(int i, Server s) throws RemoteException {
		this.untraversed = null;
		this.link = null;
		this.old_link = null;
		this.level = 0;
		this.id = i;
		this.killed = false;
		this.elected = false;
		this.server = s;
	}

	@Override
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException {
		this.untraversed = new ArrayList<ComponentInterf>(Arrays.asList(c));
		this.untraversed.remove(this);
		System.out.println(this.id + " has been initialised.");
		if(!this.killed) {
			sendNext();
		}
	}

	@Override
	public void receive(int l, int i, ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Process " + this.id + " Level " + this.level + " has received message: Level " + l + ", ID" + i);
		if(this.id == i && !this.killed){
			System.out.println("Process " + this.id + " has received acknowledgement for its own message");
			server.acknowledged++;
			this.level++;
			untraversed.remove(c);
			sendNext();
		}
		else{
			if (l < this.level || (l == this.level && i <= this.id)){ //Added <= instead of < to prevent endless looping, will have to ask TA about that
				server.ignored++;
				System.out.println("Process " + i + " is now stuck");
			}
			else{
				if(!this.killed){
					this.killed = true;
					System.out.println("Process " + this.id + " is now killed");
				}
				else{
					System.out.println("Process " + this.id + " was already killed");
				}
				server.killed++;
				send(l, i, c);

			}
		}
	}

	@Override
	public void send(int l, int i, ComponentInterf c) throws RemoteException{
			c.receive(l, i, this);
	}
	
	public void sendNext() throws RemoteException{
		if(!untraversed.isEmpty()){
			server.sent++;
			this.link = untraversed.get((int)(Math.random()*untraversed.size()));
			System.out.println("Process " + this.id + " sent message: Level " + this.level + ", ID " + this.id + " to Process " + (this.link).toString());
			send(this.level, this.id, this.link);
		}
		else{
			if(!this.killed){
				this.elected = true;
				System.out.println(this.id + " has been elected.");
				server.Elected();
			}
		}
	}

	public String toString(){
		return "" + this.id;
	}
}
