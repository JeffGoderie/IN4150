package com.interf.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TestRemote extends Remote{
	
	public boolean isLoginValid(String username) throws RemoteException;
}
