package component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Component extends UnicastRemoteObject implements ComponentInterf{
	private static final long serialVersionUID = 1L;
	public ArrayList<Link> traversed; 
	public boolean killed; 
	public int level;
	public Link owner;
	public Link potential_owner;
	public int id;
	boolean candidate;
	
	protected Component(ArrayList<Component> components, boolean new_candidate, int new_id) throws RemoteException {
		killed 	= false;
		owner 	= null;
		candidate = new_candidate;
		potential_owner = null;
		id 		= new_id;
		level 	= 0;
		
		for (Component component : components)
			traversed.add(new Link(component, this));
	}

	@Override
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receive(int level, int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(int level, int id) {
		// TODO Auto-generated method stub
		
	}
}
