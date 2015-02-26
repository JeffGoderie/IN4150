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
		
		Constant.RMI_IDS = registry.list();
		
		RemoteInterf remote1 = (RemoteInterf) registry.lookup(Constant.RMI_IDS[0]);
		RemoteInterf remote2 = (RemoteInterf) registry.lookup(Constant.RMI_IDS[1]);
		
		System.out.println(remote1.isLoginValid("test"));
		System.out.println(remote2.isLoginValid("al"));
		System.out.println(remote2.isLoginValid("test"));
	}
}
