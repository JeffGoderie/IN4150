package interf;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import clocks.Message;
import clocks.ScalarClock;

public interface RemoteInterf extends Remote{
	
	public void setAck() throws RemoteException;
	
	public void setName(String n) throws RemoteException;
	
	public String getName() throws RemoteException;
	
	public void setId(int i) throws RemoteException;
	
	public void setRegister(RemoteInterf[] RDS) throws RemoteException;
	
	public boolean isLoginValid(String username) throws RemoteException;
	
	public String broadcast() throws RemoteException, InterruptedException, FileNotFoundException,UnsupportedEncodingException;
	
	public void receive(Message msg, RemoteInterf origin) throws RemoteException, InterruptedException;

	public void acknowledge(Message msg, RemoteInterf origin) throws RemoteException;
	
	public void addMessage(String msg, int time) throws RemoteException;
	
	public ScalarClock getHead() throws RemoteException;
}
