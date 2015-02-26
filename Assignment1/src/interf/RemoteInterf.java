package interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import clocks.Message;

public interface RemoteInterf extends Remote{
	
	public boolean isLoginValid(String username) throws RemoteException;
	
	//public Message sendNext();
}
