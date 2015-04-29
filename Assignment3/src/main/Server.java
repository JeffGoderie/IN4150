package main;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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

		
		clientAmount = 5;
		
		ArrayList<Component> components = new ArrayList<Component>();
		for(int i=0; i<clientAmount; i++){
			boolean new_candidate = false;
			int new_id = i;
			Component new_component = new Component(components,new_candidate,new_id);
			components.add(new_component);
			registry.bind("Node"+(i+1), new_component );
		}		
		
		System.out.println("started");
	}
	
    public int roll() {
    	return (int)(Math.random()*6) + 1;
    }
	
}
