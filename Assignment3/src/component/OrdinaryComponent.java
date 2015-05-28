package component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import main.Server;

public class OrdinaryComponent extends UnicastRemoteObject implements ComponentInterf{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1586647361021071832L;
	
	private ComponentInterf potential_owner;
	private ComponentInterf owner;
	private int id;
	private int level;
	private int owner_id;
	private Server server;
	
	

	public OrdinaryComponent(Server s, int i) throws RemoteException {
		potential_owner = null;
		owner = null;
		id = i;
		level = -1;
		owner_id = -1;
		server = s;
	}

	@Override
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receive(int l, int i, ComponentInterf c) throws RemoteException{
		if (l < this.level || (l == this.level && i < this.owner_id)){
			server.kill_denied++;
		}
		
		else if (l > this.level || (l == this.level && i > this.owner_id)){
			this.potential_owner = c;
			this.level = l;
			this.owner_id = i;
			if(this.owner == null){
				this.owner = this.potential_owner;
				server.kill_granted++;
			}
			send(l, i, this.owner);
		}
		
		else if (l == this.level && i == this.owner_id){
			server.kill_granted++;
			this.owner = this.potential_owner;
			send(l, i, this.owner);
		}
	}

	@Override
	public void send(int level, int id, ComponentInterf c) throws RemoteException{
		// TODO Auto-generated method stub
		c.receive(level, id, this);
		
	}
	
	@Override
	public String toString(){
		return this.id + " (Ordinary)";
	}
}
