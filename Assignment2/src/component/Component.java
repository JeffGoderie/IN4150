package component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;

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
	private ArrayList<Integer> grant_Set;
	private Integer[] index_Set;
	private String name;
	private ScalarClock clockValue;
	private int grantedID;
	private boolean granted;
	private boolean CS;
	
	public Component(Integer[] qs, int i, int t) throws RemoteException {
		super();
		this.r_Q = new RequestQueue();
		this.request_Set = new ComponentInterf[qs.length];
		this.index_Set = qs;
		this.clockValue = new ScalarClock(t, i);
		this.grantedID = i;
		this.granted = false;
		this.grant_Set = new ArrayList<Integer>();
		this.CS = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException {
		// TODO Auto-generated method stub
		this.registry_Set = c;
	}
	
	@Override
	public void setRequestSet(ComponentInterf[] c) throws RemoteException {
		for(int i=0; i<index_Set.length; i++){
			request_Set[i] = c[i];
		}
	}
	
	@Override
	public void setName(String n) throws RemoteException {
		// TODO Auto-generated method stub
		this.name = n;
	}
	
	@Override
	public void request() throws RemoteException {
		// TODO Auto-generated method stub
		for (ComponentInterf c : request_Set){
			c.receiveRequest(new Request(clockValue));
		}
	}
	
	@Override
	public void receiveRequest(Request r) throws RemoteException {
		// TODO Auto-generated method stub
		if(granted){
			this.r_Q.add(r);
			if(this.r_Q.peek().equals(r)){
				inquire(registry_Set[grantedID]);
				
				
				grantedID = r.timestamp.getId();
				grant(this.registry_Set[grantedID]);
			}
			else{
				postpone(this.registry_Set[r.timestamp.getId()]);
			}
		}
		else{
			this.r_Q.add(r);
			grantedID = r.timestamp.getId();
			granted = true;
			grant(this.registry_Set[grantedID]);
		}
	}
	
	@Override
	public void grant(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		c.receiveGrant(this.clockValue.getId());
	}
	
	@Override
	public void receiveGrant(int i) throws RemoteException {
		// TODO Auto-generated method stub
		grant_Set.add(i);
		if(grant_Set.containsAll(Arrays.asList(registry_Set))){
			//Access CS
			this.CS = true;
		}
	}
	
	@Override
	public void postpone(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inquire(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void relinquish(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		this.CS = false;
		this.grant_Set.remove(c.getClockId());	
	}
	
	@Override
	public void release() throws RemoteException {
		// TODO Auto-generated method stub
		this.CS = false;
		for(ComponentInterf c : request_Set){
			c.removeHead();
		}
	}
	
	@Override
	public void removeHead() throws RemoteException {
		// TODO Auto-generated method stub
		this.r_Q.poll();
	}

	@Override
	public int getClockId() {
		return this.clockValue.getId();
	}

}
