package component;

import java.rmi.Remote;
import java.rmi.RemoteException;

import clocks.ScalarClock;
import request.Request;

public interface ComponentInterf extends Remote {
	
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException;
	
	public void setRequestSet(ComponentInterf[] c) throws RemoteException;
	
	public void request() throws RemoteException, InterruptedException;
	
	public void receiveRequest(Request r, ComponentInterf c) throws RemoteException, InterruptedException;
	
	public void grant(ComponentInterf c) throws RemoteException;
	
	public void receiveGrant() throws RemoteException;
	
	public void postpone(ComponentInterf c) throws RemoteException;
	
	public void receivePostpone() throws RemoteException;
	
	public void receiveInquire(ComponentInterf c) throws RemoteException, InterruptedException;
	
	public void inquire(ComponentInterf c) throws RemoteException, InterruptedException;
	
	public void relinquish(ComponentInterf c) throws RemoteException;
	
	public void receiveRelinquish(ComponentInterf c) throws RemoteException;
	
	public void release() throws RemoteException;
	
	public void removeHead() throws RemoteException;
	
	public int getClockId() throws RemoteException;
	
	public ScalarClock getClock() throws RemoteException;
	
}
