package component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Component extends UnicastRemoteObject implements ComponentInterf{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751606033453633216L;

	protected Component() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setRegistrySet(ComponentInterf[] c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRequestSet(ComponentInterf[] c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void request() throws RemoteException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void grant(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveGrant() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postpone(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivePostpone() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveInquire(ComponentInterf c) throws RemoteException,
			InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inquire(ComponentInterf c) throws RemoteException,
			InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void relinquish(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveRelinquish(ComponentInterf c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeHead() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getClockId() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
