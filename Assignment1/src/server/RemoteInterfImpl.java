package server;

import interf.RemoteInterf;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import processes.MessageQueue;
import clocks.Message;

public class RemoteInterfImpl extends UnicastRemoteObject implements RemoteInterf {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MessageQueue msg_q;

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
	/*
	@Override
	public Message sendNext() {
		return msg_q.message_queue.poll();
	}
*/
}
