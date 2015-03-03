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
		
		((RemoteInterf) registry.lookup(registry.list()[0])).addMessage("test1", 8);
		((RemoteInterf) registry.lookup(registry.list()[0])).addMessage("test3", 3);
		
		/*new Thread ( () -> {
			
			try {
				//Thread.sleep(10000);
				((RemoteInterf) registry.lookup(registry.list()[0])).broadcast();
			} catch (Exception e) {
				e.printStackTrace();
 	 		}
			
			
		}).start();
		
		new Thread ( () -> {
			
			try {
				Thread.sleep(10000);
				((RemoteInterf) registry.lookup(registry.list()[0])).broadcast();
			} catch (Exception e) {
				e.printStackTrace();
 	 		}
			
			
		}).start();*/
		
		((RemoteInterf) registry.lookup(registry.list()[0])).broadcast();
		System.out.println(((RemoteInterf) registry.lookup(registry.list()[0])).getHead());
	}
}
