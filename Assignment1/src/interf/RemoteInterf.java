package interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import clocks.Ack;
import clocks.Message;

public interface RemoteInterf extends Remote{
	
	public void setName(String n) throws RemoteException;
	
	public String getName() throws RemoteException;
	
	public void setRegister(RemoteInterf[] RDS) throws RemoteException;
	
	public boolean isLoginValid(String username) throws RemoteException;
	
	public void broadcast(String msg) throws RemoteException;
	
	public void receive(String msg, String origin) throws RemoteException;

	public void acknowledge(String msg, String origin) throws RemoteException;
}
