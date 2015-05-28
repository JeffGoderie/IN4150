package component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import clocks.ScalarClock;
import request.Request;
import request.RequestQueue;

public class Component extends UnicastRemoteObject implements ComponentInterf{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4573717961504130198L;
	private RequestQueue r_Q;
	private ComponentInterf[] registry_Set;
	private ComponentInterf[] request_Set;
	private Integer[] index_Set;
	private ComponentInterf grantedComponent;
	private Request grantedRequest;
	private ScalarClock clockValue;
	private boolean granted;
	private AtomicInteger no_grants;
	private AtomicInteger stepCounter;
	private boolean inquiring;
	private boolean postponed;
	private BufferedWriter output;
	
	public Component(Integer[] qs, int i, int t, AtomicInteger sc) throws RemoteException {
		super();
		this.grantedComponent = null;
		this.r_Q = new RequestQueue();
		this.request_Set = new ComponentInterf[qs.length];
		this.index_Set = qs;
		this.clockValue = new ScalarClock(t, i);
		this.granted = false;
		this.no_grants = new AtomicInteger(0);
		this.inquiring = false;
		this.postponed = false;
		this.stepCounter = sc;
		try {
			File file = new File("Client" + i +".csv");
			this.output = new BufferedWriter(new FileWriter(file));
		} catch ( IOException e ) {
			e.printStackTrace();
		};
	}


	@Override
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException {
		this.registry_Set = c;
	}
	
	@Override
	public void setRequestSet(ComponentInterf[] c) throws RemoteException {
		for(int i=0; i<index_Set.length; i++){
			request_Set[i] = c[index_Set[i]];
		}
	}
	
	@Override
	public void request() throws RemoteException, InterruptedException {
		
		// TODO Auto-generated method stub
		no_grants.set(0);

		for (ComponentInterf c : request_Set){
			this.stepCounter.incrementAndGet();
			try{
				output.write(this.stepCounter.get() + "; Sent request to Process " + c.getClockId()+ "\n");
				output.flush();
			}
			catch(IOException e){
				
			}
			//System.out.println("Process " + this.getClockId() + " has sent requests out to Process " + c.getClockId());

			new Thread ( () -> {					
				try {
					Thread.sleep((int)(Math.random()*5));
					c.receiveRequest(new Request(clockValue), this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
	
	@Override
	public ScalarClock getClock() throws RemoteException {
		// TODO Auto-generated method stub
		return this.clockValue;
	}
	
	@Override
	public void receiveRequest(Request r, ComponentInterf c) throws RemoteException, InterruptedException {
		// TODO Auto-generated method stub
		this.stepCounter.incrementAndGet();
		try{
			output.write(this.stepCounter.get() + "; Received request from Process " + c.getClockId()+ "\n");
			output.flush();
		}
		catch(IOException e){
			
		}
		//System.out.println("Process " + this.getClockId() + " has received request from Process " + c.getClockId());
		
		if(!granted){
			this.granted = true;
			this.r_Q.add(r);
			this.granted = true;
			this.grantedComponent = c;
			this.grantedRequest = r;
			this.grant(c);
		}
		else{
			
			this.r_Q.add(r);
			
			if(!r.timestamp.smallerThan(this.r_Q.peek().timestamp)||!r.timestamp.smallerThan(grantedComponent.getClock())){
				this.postpone(c);
			}
			else{
				if(!this.inquiring){
					this.inquiring = true;
					inquire(this.grantedComponent);
				}
			}
		}
	}
	
	@Override
	public void grant(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		this.stepCounter.incrementAndGet();
		try{
			output.write(this.stepCounter.get() + "; Granted to Process " + c.getClockId()+ "\n");
			output.flush();
		}
		catch(IOException e){
			
		}
		//System.out.println("Process " + this.getClockId() + " has sent grant out to Process " + c.getClockId());
		c.receiveGrant();
	}
	
	@Override
	public void receiveGrant() throws RemoteException {
		// TODO Auto-generated method stub
		this.stepCounter.incrementAndGet();
		try{
			output.write(this.stepCounter.get() + "; Received grant" + "\n");
			output.flush();
		}
		catch(IOException e){
			
		}
		//System.out.println("Process " + this.getClockId() + " has received grant");
		
		int grants = this.no_grants.incrementAndGet();
		if(grants == request_Set.length){
			this.postponed = false;
			try{
				output.write(this.stepCounter.get() + "; Can be released"+ "\n");
				output.flush();
			}
			catch(IOException e){
				
			}
			System.out.println(this.getClockId() + " Released");
			//System.out.println(this.getClockId());
			for(ComponentInterf c: request_Set){
				c.release();
			}
		}
	}
	
	@Override
	public void postpone(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		this.stepCounter.incrementAndGet();
		try{
			output.write(this.stepCounter.get() + "; Sent postpone to Process " + c.getClockId()+ "\n");
			output.flush();
		}
		catch(IOException e){
			
		}
		//System.out.println("Process " + this.getClockId() + " has sent postpone out to Process " + c.getClockId());
		c.receivePostpone();
	}

	@Override
	public void receivePostpone() throws RemoteException {
		// TODO Auto-generated method stub
		this.stepCounter.incrementAndGet();
		try{
			output.write(this.stepCounter.get() + "; Received postpone"+ "\n");
			output.flush();
		}
		catch(IOException e){
			
		}
		//System.out.println("Process " + this.getClockId() + " has received postpone");
		
		this.postponed = true;
	}
	
	@Override
	public void inquire(ComponentInterf c) throws RemoteException, InterruptedException {
		// TODO Auto-generated method stub
		this.stepCounter.incrementAndGet();
		try{
			output.write(this.stepCounter.get() + "; Sent inquire to Process " + c.getClockId()+ "\n");
			output.flush();
		}
		catch(IOException e){
			
		}
		//System.out.println("Process " + this.getClockId() + " has sent inquire out to Process " + c.getClockId());
		
		c.receiveInquire(this);
	}

	@Override
	public void receiveInquire(ComponentInterf c) throws RemoteException, InterruptedException {
		// TODO Auto-generated method stub
		this.stepCounter.incrementAndGet();
		try{
			output.write(this.stepCounter.get() + "; Received inquire from " + c.getClockId()+ "\n");
			output.flush();
		}
		catch(IOException e){
			
		}
		//System.out.println("Process " + this.getClockId() + " has received inquire from Process " + c.getClockId());
		
		while(!this.postponed && this.no_grants.get()<this.request_Set.length){
			Thread.sleep(5);
		}
		if(this.postponed){
			this.no_grants.decrementAndGet();
			this.relinquish(c);
		}
	}
	
	@Override
	public void relinquish(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		this.stepCounter.incrementAndGet();
		try{
			output.write(this.stepCounter.get() + "; Sent relinquish to Process " + c.getClockId()+ "\n");
			output.flush();
		}
		catch(IOException e){
			
		}
		//System.out.println("Process " + this.getClockId() + " has sent relinquish out to Process " + c.getClockId());
		
		c.receiveRelinquish(this);	
	}
	
	@Override
	public void receiveRelinquish(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		this.stepCounter.incrementAndGet();
		try{
			output.write(this.stepCounter.get() + "; Received relinquish from Process " + c.getClockId()+ "\n");
			output.flush();
		}
		catch(IOException e){
			
		}
		//System.out.println("Process " + this.getClockId() + " has received relinquish");
		
		this.inquiring = false;
		this.granted = false;
		this.grantedComponent = this.registry_Set[this.r_Q.peek().timestamp.getId()];
		this.grantedRequest = this.r_Q.peek();
		this.granted = true;
		this.grant(this.grantedComponent);
	}
	
	@Override
	public void release() throws RemoteException {
		this.stepCounter.incrementAndGet();
		try{
			output.write(this.stepCounter.get() + "; Releasing Process " + this.grantedRequest.timestamp.getId()+ "\n");
			output.flush();
		}
		catch(IOException e){
			
		}
		this.granted = false;
		this.inquiring = false;
		this.r_Q.remove(this.grantedRequest);
		this.grantedComponent = null;
		this.grantedRequest = null;
		if(this.r_Q.peek()!=null){
			this.grantedComponent = this.registry_Set[this.r_Q.peek().timestamp.getId()];
			this.grantedRequest = this.r_Q.peek();
			this.granted = true;
			this.grant(this.grantedComponent);
		}
		else{

		}
	}
	
	@Override
	public void removeHead() throws RemoteException {
		this.r_Q.poll();
	}

	@Override
	public int getClockId() throws RemoteException {
		return this.clockValue.getId();
	}
}
