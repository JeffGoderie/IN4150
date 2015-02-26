package interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterf extends Remote{
	
	public boolean isLoginValid(String username) throws RemoteException;
}
