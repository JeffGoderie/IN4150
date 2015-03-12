package main;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import component.Component;
import component.ComponentInterf;

public class Server {
	
	public Registry registry;
	public int clientAmount;
	private int Port = 8017;
	public ComponentInterf[] RMI_IDS;
	
	public void main() throws AlreadyBoundException, NotBoundException, IOException{
		registry = LocateRegistry.createRegistry(Port);
		
		Scanner a = new Scanner(System.in);
		System.out.println("Please enter the amount of clients:");
		clientAmount = a.nextInt();
		System.out.println("Please enter request set size (between " + (int) Math.ceil(Math.sqrt(clientAmount)) + " and " + clientAmount + ").");
		int setSize = a.nextInt();
		while (setSize > clientAmount || setSize < Math.ceil(Math.sqrt(clientAmount))){
			if(setSize > clientAmount){
				System.out.println("Set size can't exceed the amount of clients.");
				System.out.println("Please enter a value between " + (int) Math.ceil(Math.sqrt(clientAmount)) + " and " + clientAmount + ".");
				setSize = a.nextInt();
			}
			else if(setSize < Math.ceil(Math.ceil(Math.sqrt(clientAmount)))){
				System.out.println("Set size can't be less than the square root of the amount of clients.");
				System.out.println("Please enter a value between " + (int) Math.ceil(Math.sqrt(clientAmount)) + " and " + clientAmount + ".");
				setSize = a.nextInt();	
			}
		}

		a.close();
		
		// To mimic the example on slides
		clientAmount = 7;
		setSize = 3;
		
		Integer[][] sets = {{0,1,2},{1,4,6},{2,3,4},{0,3,6},{0,4,5},{1,3,5},{2,5,6}};
		
		
		for(int i=0; i<clientAmount; i++){
			//int[] set = new int[setSize];
			//for(int j=0; j<setSize; j++){
			//	set[j] = (i+j)%clientAmount;
			//}
			registry.bind("Client"+(i+1), new Component(sets[i], (int)(Math.random()*clientAmount*2) , i));
		}		

		setRegistry();
		totalRequest();
		
		System.out.println("started");
	}
	
	public void setRegistry() throws RemoteException, NotBoundException{
		registry = LocateRegistry.getRegistry("localhost", Port);
		RMI_IDS = new ComponentInterf[registry.list().length];
		for(int i=0; i<registry.list().length; i++){
			RMI_IDS[i] = (ComponentInterf) registry.lookup(registry.list()[i]);
		}
		for(int i=0; i<RMI_IDS.length; i++){
			RMI_IDS[i].setRegistrySet(RMI_IDS);
			RMI_IDS[i].setRequestSet(RMI_IDS);
			RMI_IDS[i].setName(registry.list()[i]);
		}
	}
	
	public  void totalRequest() throws RemoteException, NotBoundException{
		for(int i = 0; i<RMI_IDS.length; i++){
			ComponentInterf RMI = RMI_IDS[i];
			new Thread ( () -> {					
				try {
					Thread.sleep((int)(Math.random()*500));
					RMI.request();
				} catch (Exception e) {
					e.printStackTrace();
	 	 		}
			}).start();
		}
	}
}
