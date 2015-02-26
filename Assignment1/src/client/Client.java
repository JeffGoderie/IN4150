package client;

import interf.Constant;
import interf.RemoteInterf;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	public static void main(String[] args) throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		RemoteInterf remote = (RemoteInterf) registry.lookup(Constant.RMI_ID);
		System.out.println(remote.isLoginValid("al"));
		System.out.println(remote.isLoginValid("test"));
		
	}
}
