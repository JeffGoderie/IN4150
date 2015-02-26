package server;

import interf.Constant;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIserver {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException{
		RemoteInterfImpl impl = new RemoteInterfImpl();
		Registry registry = LocateRegistry.createRegistry(Constant.RMI_PORT);
		registry.bind(Constant.RMI_ID, impl);
		registry.bind("test2", impl);
		System.out.println("started");
	}
}
