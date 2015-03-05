package server;

import interf.RemoteInterf;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import processes.MessageQueue;
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
	
	private int receiveCount = 0;
	
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
	public String broadcast() throws RemoteException, InterruptedException, FileNotFoundException, UnsupportedEncodingException {		
		String output="";
		if(msg_q.peek()==null){
			return "";
		}
		System.out.println("Broadcasting Message: " + msg_q.peek() + " from " + this.name);
		for(int i=0; i<RD.length; i++){
			RemoteInterf RDi = RD[i];
			new Thread ( () -> {			
				try {
					//Thread.sleep(10000);
					RDi.receive(msg_q.peek(), this);
				} catch (Exception e) {
					e.printStackTrace();
	 	 		}		
			}).start();
			
		}
		int i = 0;
		while(this.receiveCount < RD.length*RD.length){
			i++;
			Thread.sleep(100);
			if(i==100){
				break;
			}
		}
		this.receiveCount = 0;
		System.out.println("Ack_count: " + ack_count);
		if(this.ack_count==RD.length){
			output = "Message: '"+msg_q.peek()+"' has been delivered.";
			System.out.println(output);
			msg_q.poll();
			this.ack_count = 0;
		}
		else{
			this.ack_count = 0;
		}
		System.out.println("Broadcast complete");
		return output;
	}
	
	public void receive(Message msg, RemoteInterf origin) throws RemoteException{
		if(msg.timestamp.smallerThan(this.getHead())){
			for(int i=0; i<RD.length; i++){
				RemoteInterf RDi = RD[i];
				new Thread ( () -> {
				
					try {
						//Thread.sleep(10000);
						RDi.acknowledge(msg, origin);
					} catch (Exception e) {
						e.printStackTrace();
		 	 		}
				
				
				}).start();
			}
		}
		else{
			origin.setRC();
		}
	}
	
	public ScalarClock getHead() throws RemoteException{
		if(msg_q.peek()!=null){
			return msg_q.peek().timestamp;
		}
		return null;
	}
	
	public void setRegister(RemoteInterf[] RDS) throws RemoteException{
		this.RD = RDS;
	}

	@Override
	public void acknowledge(Message msg, RemoteInterf origin) throws RemoteException {
		origin.setRC();
		if(this.getName().equals(origin.getName())){
			origin.setAck();
		}
	}
	
	@Override
	public void setAck(){
		this.ack_count++;
	}

	public void setBrC(){
	}
	
	public void setRC(){
		this.receiveCount++;
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
