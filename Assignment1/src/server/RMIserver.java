package server;

import interf.Constant;
import interf.RemoteInterf;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIserver {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException{
		Registry registry = LocateRegistry.createRegistry(Constant.RMI_PORT);
		registry.bind(Constant.RMI_ID, new RemoteInterfImpl());
		registry.bind("test2", new RemoteInterfImpl());
		setRegistry();
		System.out.println("started");
	}
	
	public static void setRegistry() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		RemoteInterf[] RMI_IDS = new RemoteInterf[registry.list().length];
		for(int i=0; i<registry.list().length; i++){
			RMI_IDS[i] = (RemoteInterf) registry.lookup(registry.list()[i]);
		}
		for(int i=0; i<RMI_IDS.length; i++){
			RMI_IDS[i].setRegister(RMI_IDS);
			RMI_IDS[i].setName(registry.list()[i]);
		}
	}
}
