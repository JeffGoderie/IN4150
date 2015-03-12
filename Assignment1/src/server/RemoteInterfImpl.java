package server;

import interf.RemoteInterf;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

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
	private AtomicInteger Rec;
	
	protected RemoteInterfImpl() throws RemoteException {
		super();
		this.msg_q = new MessageQueue();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void broadcast() throws RemoteException, InterruptedException, FileNotFoundException, UnsupportedEncodingException {		
		String output="";
		if(msg_q.peek()!=null){
			Rec = new AtomicInteger(RD.length);
			for(int i=0; i<RD.length; i++){
				RemoteInterf RDi = RD[i];
				new Thread ( () -> {			
					try {
						//Thread.sleep(10000);
						RDi.receive(msg_q.peek(), this);
						Rec.getAndDecrement();
					} catch (Exception e) {
						e.printStackTrace();
		 	 		}		
				}).start();
				
			}
			while(this.Rec.get() > 0){
				Thread.sleep(1);
			}
			if(this.ack_count==RD.length){
				Message sent = msg_q.poll();
				System.out.println("Message: '"+sent+"' has been delivered.");
				this.ack_count = 0;
			}
			else{
				this.ack_count = 0;
			}
		}
	}
	
	public void receive(Message msg, RemoteInterf origin) throws RemoteException, InterruptedException{
		//Check whether message is at the head of the message queue
		if(msg.timestamp.smallerThan(this.getHead())){
			for(int i=0; i<RD.length; i++){
				RD[i].acknowledge(msg, origin);
			}
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

	//Not completely failsafe, would have been better to use identifiers instead of name (duplicate client names are allowed)
	@Override
	public void acknowledge(Message msg, RemoteInterf origin) throws RemoteException {
		if(this.getName().equals(origin.getName())){
			origin.setAck();
		}
	}
	
	@Override
	public void setAck(){
		this.ack_count++;
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
