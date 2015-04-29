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
