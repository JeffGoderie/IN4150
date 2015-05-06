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
	private int Port = 8017;
	public ComponentInterf[] RMI_IDS;
	
	public void main() throws AlreadyBoundException, NotBoundException, IOException{
		registry = LocateRegistry.createRegistry(Port);

		
		candidateAmount = 15;
		ordinaryAmount = 15;
		
		ArrayList<ComponentInterf> components = new ArrayList<ComponentInterf>();
		for(int i=0; i<candidateAmount; i++){
			CandidateComponent new_component = new CandidateComponent(i+10);
			components.add(new_component);
			registry.bind("CC"+(i+1), new_component );
		}
		for(int i=0; i<ordinaryAmount; i++){
			OrdinaryComponent new_component = new OrdinaryComponent();
			components.add(new_component);
			registry.bind("OC"+(i+1), new_component );
		}	
		
		new Thread ( () -> {					
			try {
				System.out.println("started");
				//Thread.sleep((int)(Math.random()*50));
				setRegistry();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		
		
	}
	
	public void setRegistry() throws RemoteException, NotBoundException{
		//registry = LocateRegistry.getRegistry("localhost", Port);
		RMI_IDS = new ComponentInterf[registry.list().length];
		for(int i=0; i<registry.list().length; i++){
			RMI_IDS[i] = (ComponentInterf) registry.lookup(registry.list()[i]);
		}
		for(int i=0; i<RMI_IDS.length; i++){
			ComponentInterf RMI_ID_SELECTED = RMI_IDS[i];
			new Thread ( () -> {					
				try {
					Thread.sleep((int)(Math.random()*50));
					RMI_ID_SELECTED.setRegistrySet(RMI_IDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
}
