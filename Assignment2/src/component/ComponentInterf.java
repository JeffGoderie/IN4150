package component;

import java.rmi.Remote;
import java.rmi.RemoteException;

import request.Request;

public interface ComponentInterf extends Remote {
	
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException;
	
	public void setRequestSet(ComponentInterf[] c) throws RemoteException;
	
	public void setName(String n) throws RemoteException;
	
	public void request() throws RemoteException;
	
	public void receiveRequest(Request r) throws RemoteException;
	
	public void grant(ComponentInterf c) throws RemoteException;
	
	public void receiveGrant(int i) throws RemoteException;
	
	public void postpone(ComponentInterf c) throws RemoteException;
	
	public void inquire(ComponentInterf c) throws RemoteException;
	
	public void relinquish(ComponentInterf c) throws RemoteException;
	
	public void release() throws RemoteException;
	
	public void removeHead() throws RemoteException;
	
	public int getClockId();
}
