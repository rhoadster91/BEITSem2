package beit.skn.rhoadster91;

import java.rmi.Naming;


public class RMIServer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String []args)
	{
		try
		{
			RMIImpl rmiserverimpl = new RMIImpl();
			Naming.rebind("GKRMIServer", rmiserverimpl);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
