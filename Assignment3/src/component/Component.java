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
	
	public Component(ArrayList<Component> components, boolean new_candidate, int new_id) throws RemoteException {
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
		/* Psuedocode from slides
		while (untraversed )= ∅) do
			link ← any untraversed link
			send(level,id) on link
			R: receive(level’,id’) on link’
				if ((id=id’) and (killed=false)) then
					level ← level+1
					untraversed ← untraversed \ link
				else
					if ((level’,id’) < (level,id)) then goto R
					else
						send(level’,id’) on link’
						killed ← true
						goto R
		if (killed = false) then elected ← true
		 */
		
	}

	@Override
	public void send(int level, int id) {
		/*Psuedocode from slides
	 do forever
		receive(level’,id’) on link’
		case (level’,id’) of
			(level’,id’) < (level,owner-id): ignore
			(level’,id’) > (level,owner-id):
				potential-owner ← link’
				(level,owner-id) ← (level’,id’)
				if (owner=nil) then owner ← potential-owner
				send(level’,id’) on owner-link
			(level’,id’) = (level,owner-id):
				owner ← potential-owner
				send(level’,id’) on owner-link
		 */		
	}
}
