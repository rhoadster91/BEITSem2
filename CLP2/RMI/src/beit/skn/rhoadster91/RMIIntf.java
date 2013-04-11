package beit.skn.rhoadster91;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface RMIIntf extends Remote 
{
	public HashMap<Integer, String> select() throws RemoteException;
	public int insert(int rno, String name) throws RemoteException;
	public int update(int rollNumber, String name) throws RemoteException;
	public int delete(int rollNumber) throws RemoteException;
}
