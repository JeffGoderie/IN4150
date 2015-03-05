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

import server.RemoteInterfImpl;

public class Client {

	public static void main(String[] args) throws NotBoundException, IOException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		
		BufferedReader br = new BufferedReader(new FileReader("tests/messages.txt"));
		String line = "";
		String[] split_line;
		while ((line = br.readLine()) != null) {
			split_line = line.split(" ");
			((RemoteInterf) registry.lookup(registry.list()[Integer.parseInt(split_line[0])])).addMessage(split_line[1], 8);
		}
		br.close();
		
		//((RemoteInterf) registry.lookup(registry.list()[0])).addMessage("test1", 8);
		//((RemoteInterf) registry.lookup(registry.list()[0])).addMessage("test3", 3);
		
		/*new Thread ( () -> {
			
			try {
				//Thread.sleep(10000);
				((RemoteInterf) registry.lookup(registry.list()[0])).broadcast();
			} catch (Exception e) {
				e.printStackTrace();
 	 		}
			
			
		}).start();
		
		new Thread ( () -> {
			
			try {
				Thread.sleep(10000);
				((RemoteInterf) registry.lookup(registry.list()[0])).broadcast();
			} catch (Exception e) {
				e.printStackTrace();
 	 		}
			
			
		}).start();*/
		
		((RemoteInterf) registry.lookup(registry.list()[0])).broadcast();
		System.out.println(((RemoteInterf) registry.lookup(registry.list()[0])).getHead());
	}
}
