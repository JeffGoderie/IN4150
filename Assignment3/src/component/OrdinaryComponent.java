package component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class OrdinaryComponent extends UnicastRemoteObject implements ComponentInterf{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1586647361021071832L;
	
	private ComponentInterf potential_owner;
	private ComponentInterf owner;
	private int level;
	private int owner_id;
	
	

	public OrdinaryComponent() throws RemoteException {
		potential_owner = null;
		owner = null;
		level = -1;
		owner_id = -1;
	}

	@Override
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receive(int l, int i, ComponentInterf c) throws RemoteException{
		if (l < this.level || (l == this.level && i < this.owner_id)){
			
		}
		
		if (l > this.level || (l == this.level && i > this.owner_id)){
			this.potential_owner = c;
			this.level = l;
			this.owner_id = i;
			if(this.owner == null){
				this.owner = this.potential_owner;
			}
			send(l, i, this.owner);
		}
		
		if (l == this.level && i == this.owner_id){
			this.owner = this.potential_owner;
			send(l, i, this.owner);
		}
	}

	@Override
	public void send(int level, int id, ComponentInterf c) throws RemoteException{
		// TODO Auto-generated method stub
		c.receive(level, id, this);
		
	}
}
