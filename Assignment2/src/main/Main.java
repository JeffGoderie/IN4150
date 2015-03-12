package main;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import component.ComponentInterf;

public class Main {
	
	public void main() throws AlreadyBoundException, NotBoundException, IOException{
		Server server = new Server();
		int accessed = 0;
		server.main();
		while(accessed<server.clientAmount){
			for(ComponentInterf c: server.RMI_IDS){
				if(c.updateCounter()){
					accessed++;
				}
			}
		}
	}
}
