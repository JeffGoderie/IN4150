package main;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import component.CandidateComponent;
import component.ComponentInterf;
import component.OrdinaryComponent;

public class Server {
	
	public Registry registry;
	public int candidateAmount;
	public int ordinaryAmount;
	private int Port = 8018;
	public ComponentInterf[] RMI_IDS;
	
	public int killed;
	public int final_killed;
	public int ignored;
	public int final_ignored;
	public int acknowledged;
	public int final_ack;
	public int kill_granted;
	public int final_kill_granted;
	public int kill_denied;
	public int final_kill_denied;
	public int sent;
	public int final_sent;
	public boolean elected;
	
	public void main() throws AlreadyBoundException, NotBoundException, IOException{
		registry = LocateRegistry.createRegistry(Port);

		
		candidateAmount = 2;
		ordinaryAmount = 2;
		
		ArrayList<ComponentInterf> components = new ArrayList<ComponentInterf>();
		for(int i=0; i<candidateAmount; i++){
			CandidateComponent new_component = new CandidateComponent(i+ordinaryAmount, this);
			components.add(new_component);
			registry.bind("CC"+(i+1), new_component );
		}
		for(int i=0; i<ordinaryAmount; i++){
			OrdinaryComponent new_component = new OrdinaryComponent(this, i);
			components.add(new_component);
			registry.bind("OC"+(i+1), new_component );
		}	
		
		System.out.println("started");
		setRegistry();
		
	}
	
	public void setRegistry() throws RemoteException, NotBoundException{
		//registry = LocateRegistry.getRegistry("localhost", Port);
		RMI_IDS = new ComponentInterf[registry.list().length];
		for(int i=0; i<registry.list().length; i++){
			RMI_IDS[i] = (ComponentInterf) registry.lookup(registry.list()[i]);
		}
		//((ComponentInterf)registry.lookup("CC500")).setRegistrySet(RMI_IDS);
		for(int i=0; i<RMI_IDS.length; i++){
			ComponentInterf RMI_ID_SELECTED = RMI_IDS[i];
			new Thread ( () -> {					
				try {
					//Thread.sleep((int)(Math.random()*50));
					RMI_ID_SELECTED.setRegistrySet(RMI_IDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
	
	public void Elected() throws RemoteException{
		if(!elected){
			elected = true;
			final_ack = acknowledged;
			final_ignored = ignored;
			final_kill_denied = kill_denied;
			final_kill_granted = kill_granted;
			final_killed = killed;
			final_sent = sent;
			
			System.out.println("Ack: " + final_ack);
			//System.out.println(final_ignored);
			//System.out.println(final_kill_denied);
			System.out.println("Captures: " + final_kill_granted);
			System.out.println("Killed: " + final_killed);
			System.out.println("Sent: " + final_sent);
		}
		
	}
}
