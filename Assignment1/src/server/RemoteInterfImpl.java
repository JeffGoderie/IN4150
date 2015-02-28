package server;

import interf.RemoteInterf;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import processes.MessageQueue;
import clocks.Ack;
import clocks.Message;

public class RemoteInterfImpl extends UnicastRemoteObject implements RemoteInterf {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private MessageQueue msg_q;
	private RemoteInterf[] RD;
	
	protected RemoteInterfImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isLoginValid(String username) throws RemoteException {
		if(username.equals("test"))
			return true;
		return false;
	}
	
	@Override
	public void broadcast(String msg) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("I, " + name + ", have sent message " + msg);
		for(int i=0; i<RD.length; i++){
			RD[i].receive(msg, name);
		}
	}
	
	public void receive(String msg, String origin) throws RemoteException{
		System.out.println("I, " + name + ", have received message " + msg + " from " + origin);
		for(int i=0; i<RD.length; i++){
			RD[i].acknowledge(msg, name);
		}
	}

	public void setRegister(RemoteInterf[] RDS) throws RemoteException{
		// TODO Auto-generated method stub
		this.RD = RDS;
	}

	@Override
	public void acknowledge(String msg, String origin) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("I, " + name + ", have received acknowledgement for message " + msg + " from " + origin);
	}

	@Override
	public void setName(String n) throws RemoteException {
		// TODO Auto-generated method stub
		this.name = n;
	}

	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return name;
	}
}
