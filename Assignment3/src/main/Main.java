package main;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

public class Main {
	
	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, IOException{
		Server server = new Server();
		server.main();
	}
}
