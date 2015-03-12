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
	
	private static int Port = 8017;
	
	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, IOException{
		Registry registry = LocateRegistry.createRegistry(Port);
		
		Scanner a = new Scanner(System.in);
		System.out.println("Please enter the amount of clients:");
		int clientAmount = a.nextInt();
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
		
		System.out.println("started");
	}
	
	public static void setRegistry() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost", Port);
		ComponentInterf[] RMI_IDS = new ComponentInterf[registry.list().length];
		for(int i=0; i<registry.list().length; i++){
			RMI_IDS[i] = (ComponentInterf) registry.lookup(registry.list()[i]);
		}
		for(int i=0; i<RMI_IDS.length; i++){
			RMI_IDS[i].setRegistrySet(RMI_IDS);
			RMI_IDS[i].setRequestSet(RMI_IDS);
			RMI_IDS[i].setName(registry.list()[i]);
		}
	}
}
