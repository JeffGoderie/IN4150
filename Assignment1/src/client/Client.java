package client;

import interf.Constant;
import interf.RemoteInterf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;

import server.RemoteInterfImpl;

public class Client {

	public static void main(String[] args) throws NotBoundException, IOException, InterruptedException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		
		BufferedReader br = new BufferedReader(new FileReader("tests/messages.txt"));
		String line = "";
		String[] split_line;
		while ((line = br.readLine()) != null) {
			split_line = line.split(" ");
			((RemoteInterf) registry.lookup(registry.list()[Integer.parseInt(split_line[0])])).addMessage(split_line[1], Integer.parseInt(split_line[2]));
		}
		br.close();
		totalOrder();
		/*
		new Thread ( () -> {
			
			try {
				//Thread.sleep(10000);
				((RemoteInterf) registry.lookup(registry.list()[0])).broadcast();
			} catch (Exception e) {
				e.printStackTrace();
 	 		}
		
		
		}).start();
		
		new Thread ( () -> {
			
			try {
				//Thread.sleep(10000);
				((RemoteInterf) registry.lookup(registry.list()[1])).broadcast();
			} catch (Exception e) {
				e.printStackTrace();
 	 		}
		
		
		}).start();*/
	}
	
	public static void totalOrder() throws RemoteException, NotBoundException, InterruptedException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		RemoteInterf[] RMI_IDS = new RemoteInterf[registry.list().length];
		for(int i=0; i<registry.list().length; i++){
			RMI_IDS[i] = (RemoteInterf) registry.lookup(registry.list()[i]);
		}
		CountDownLatch doneSignal = new CountDownLatch(RMI_IDS.length);
		for(int i=0; i<RMI_IDS.length; i++){
			RemoteInterf RDi = RMI_IDS[i];

			new Thread ( () -> {
				
				try {
					RDi.broadcast();
					doneSignal.countDown();
				} catch (Exception e) {
					e.printStackTrace();
	 	 		}
			
			
			}).start();
		}
		doneSignal.await();
	}
}
