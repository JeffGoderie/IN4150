package server;

import interf.RemoteInterf;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import processes.MessageQueue;
import clocks.Ack;
import clocks.Message;
import clocks.ScalarClock;

public class RemoteInterfImpl extends UnicastRemoteObject implements RemoteInterf {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private MessageQueue msg_q;
	private RemoteInterf[] RD;
	private int ack_count;
	
	protected RemoteInterfImpl() throws RemoteException {
		super();
		this.msg_q = new MessageQueue();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isLoginValid(String username) throws RemoteException {
		if(username.equals("test"))
			return true;
		return false;
	}
	
	@Override
	public void broadcast() throws RemoteException {
		// TODO Auto-generated method stub
		
		for(int i=0; i<RD.length; i++){
			RemoteInterf RDi = RD[i];
			RDi.receive(msg_q.peek(), this);
		}
		if(this.ack_count==RD.length){
			msg_q.poll();
			this.ack_count = 0;
		}
		else{
			this.ack_count = 0;
		}
	}
	
	public void receive(Message msg, RemoteInterf origin) throws RemoteException{
		System.out.println(name + " has received message " + msg + " from " + origin.getName());
		System.out.println("Head: "+ this.getHead());
		System.out.println("Message: "+ msg.timestamp.toString());
		if(msg.timestamp.smallerThan(this.getHead())){
			for(int i=0; i<RD.length; i++){
				RemoteInterf RDi = RD[i];
				RDi.acknowledge(msg, origin);
			}
		}
		else{
			System.out.println("Error******* " + name);
		}

	}
	
	public ScalarClock getHead() throws RemoteException{
		if(msg_q.peek()!=null){
			return msg_q.peek().timestamp;
		}
		return null;
	}
	
	public void setRegister(RemoteInterf[] RDS) throws RemoteException{
		// TODO Auto-generated method stub
		this.RD = RDS;
	}

	@Override
	public void acknowledge(Message msg, RemoteInterf origin) throws RemoteException {
		// TODO Auto-generated method stub
		if(this.getName().equals(origin.getName())){
			origin.setAck();
		}

		System.out.println(name + " has received acknowledgement for message " + msg + " from " + origin.getName());
	}
	
	@Override
	public void setAck(){
		this.ack_count++;
		System.out.println(this.ack_count);
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
	
	@Override
	public void setId(int i) throws RemoteException {
		this.id = i;
	}
	
	@Override
	public void addMessage(String msg, int time) throws RemoteException{
		this.msg_q.add(new Message(msg, new ScalarClock(time, this.id)));
	}
}
