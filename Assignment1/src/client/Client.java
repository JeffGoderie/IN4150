package client;

import interf.Constant;
import interf.RemoteInterf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
		
		for(int j=0; j<num_messages; j++){
			AtomicInteger runs = new AtomicInteger(RMI_IDS.length);
			for(int i=0; i<RMI_IDS.length; i++){
				RemoteInterf RDi = RMI_IDS[i];
				new Thread ( () -> {					
					try {
						Thread.sleep((int)(Math.random()*500));
						RDi.broadcast();
						runs.getAndDecrement();

					} catch (Exception e) {
						e.printStackTrace();
		 	 		}
				
				
				}).start();
			}
			while(runs.get()!=0){
				Thread.sleep(1);
			}
		}
	}
}
