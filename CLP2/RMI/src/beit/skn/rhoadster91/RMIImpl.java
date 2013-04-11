package beit.skn.rhoadster91;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class RMIImpl extends UnicastRemoteObject implements RMIIntf
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1858503607245079774L;

	protected RMIImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	static Connection con = null;
	static
	{
		try 
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:studentdb");
			System.out.println("Connection established");
		}
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public HashMap<Integer, String> select() throws RemoteException
	{
		HashMap<Integer, String> hm = null;
		ResultSet rs = null;
		Statement st = null;
		try
		{
			st = con.createStatement();
			rs = st.executeQuery("select * from student");
			hm = new HashMap<Integer, String>();
			while(rs.next())
			{
				int rno = rs.getInt(1);
				String name = rs.getString(2);
				hm.put(new Integer(rno), name);				
			}
			return hm;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int insert(int rno, String name) throws RemoteException 
	{
		try 
		{
			
			con.prepareStatement("insert into student values(" + rno + ", '" + name + "')").execute();
			
			return 1;				
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return 0;
			
		}		
	}

	@Override
	public int update(int rollNumber, String name) throws RemoteException 
	{
		try 
		{
			
			con.prepareStatement("update student set name = '" + name + "' where rollNo = " + rollNumber).execute();
			return 1;				
		} 
		catch (SQLException e) 
		{
			return 0;
			
		}		
	}

	@Override
	public int delete(int rollNumber) throws RemoteException 
	{
		try 
		{
			
			con.prepareStatement("delete from student where rollno = " + rollNumber).execute();
			return 1;				
		} 
		catch (SQLException e) 
		{
			return 0;
			
		}		
	}

}
