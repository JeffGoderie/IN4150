package client;

import interf.Constant;
import interf.RemoteInterf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {
	static int num_messages=0;
	public static void main(String[] args) throws NotBoundException, IOException, InterruptedException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		
		BufferedReader br = new BufferedReader(new FileReader("tests/messages.txt"));
		String line = "";
		String[] split_line;
		while ((line = br.readLine()) != null) {
			num_messages++;
			split_line = line.split(" ");
			((RemoteInterf) registry.lookup(registry.list()[Integer.parseInt(split_line[0])])).addMessage(split_line[1], Integer.parseInt(split_line[2]));
		}
		br.close();
		
		totalOrder();
	}
	
	public static void totalOrder() throws NotBoundException, InterruptedException, IOException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		RemoteInterf[] RMI_IDS = new RemoteInterf[registry.list().length];
		for(int i=0; i<registry.list().length; i++){
			RMI_IDS[i] = (RemoteInterf) registry.lookup(registry.list()[i]);
		}
		AtomicInteger runs = new AtomicInteger(num_messages);
		while(runs.get()>0){
			for(int i=0; i<RMI_IDS.length; i++){
				RemoteInterf RDi = RMI_IDS[i];
				new Thread ( () -> {					
					try {
						String output = RDi.broadcast();
						if (!output.equals("")){
							runs.getAndDecrement();
						}
					} catch (Exception e) {
						e.printStackTrace();
		 	 		}
				
				
				}).start();
			}
		}
	}
}
