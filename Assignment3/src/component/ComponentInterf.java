package component;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ComponentInterf extends Remote {
	
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException;
		
	public void receive(int level, int id, ComponentInterf c) throws RemoteException;
	
	public void send(int level, int id, ComponentInterf c) throws RemoteException;
}
