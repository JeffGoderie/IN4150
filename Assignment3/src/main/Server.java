package main;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

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
		Integer[] times = {1,10,8,3,9,6,5};
		AtomicInteger stepCounter = new AtomicInteger(0);
		

		for(int i=0; i<clientAmount; i++){
			//int[] set = new int[setSize];
			//for(int j=0; j<setSize; j++){
			//	set[j] = (i+j)%clientAmount;
			//}
			registry.bind("Client"+(i+1), new Component());
		}		
		
		System.out.println("started");
	}
	
}
