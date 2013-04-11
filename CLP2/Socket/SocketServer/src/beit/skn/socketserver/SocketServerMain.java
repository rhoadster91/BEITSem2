package beit.skn.socketserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerMain
{
	static ServerSocket server;
	static Socket socket;
	static ObjectInputStream objIn;
	static ObjectOutputStream objOut;
	
	public static void main(String []args)
	{
		int ch;
		int numRequest, numReply;
		String strRequest, strRequest2, strReply;
		try 
		{
			server = new ServerSocket(9456);
			System.out.println("Server started. Waiting for client.");
			socket = server.accept();
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());
			System.out.println("Client connected. Waiting for requests.");
			do
			{
				ch = ((Integer)objIn.readObject()).intValue();
				switch(ch)
				{
				case 1:
					numRequest = ((Integer)objIn.readObject()).intValue();
					numReply = digitAddition(numRequest);
					System.out.println("Performing digit addition of " + numRequest);
					System.out.println("Pushing answer " + numReply + " to client");
					objOut.writeObject(new Integer(numReply));					
					break;
					
				case 2:
					numRequest = ((Integer)objIn.readObject()).intValue();
					numReply = factorial(numRequest);
					System.out.println("Performing factorial of " + numRequest);
					System.out.println("Pushing answer " + numReply + " to client");					
					objOut.writeObject(new Integer(numReply));					
					break;
					
				case 31:
					strRequest = (String)objIn.readObject();
					numReply = strRequest.length();
					System.out.println("Calculating length of " + strRequest);
					System.out.println("Pushing answer " + numReply + " to client");					
					objOut.writeObject(new Integer(numReply));
					break;
					
				case 32:
					strRequest = (String)objIn.readObject();
					strRequest2 = (String)objIn.readObject();
					System.out.println("Comparing " + strRequest + " and " + strRequest2);					
					if(strRequest.contentEquals(strRequest2))
						numReply = 1;
					else
						numReply = 0;
					objOut.writeObject(new Integer(numReply));
					break;
					
				case 33:
					strRequest = (String)objIn.readObject();
					strRequest2 = (String)objIn.readObject();
					System.out.println("Concatenating " + strRequest + " and " + strRequest2);					
					strReply = strRequest.concat(strRequest2);
					System.out.println("Pushing answer " + strReply+ " to client");
					objOut.writeObject(strReply);
					break;
					
				case 34:
					strRequest = (String)objIn.readObject();
					System.out.println("Checking " + strRequest+ " for palindrome");
					if(strRequest.contentEquals(reverse(strRequest)))					
						numReply = 1;
					else
						numReply = 0;
					objOut.writeObject(new Integer(numReply));					
					break;
					
				case 35:
					strRequest = (String)objIn.readObject();
					strRequest2 = (String)objIn.readObject();
					System.out.println("Performing substriung tests for " + strRequest + " and " +  strRequest2);
					if(strRequest.contentEquals(strRequest2))
					{
						numReply = 2;
						objOut.writeObject(new Integer(numReply));
						break;
					}
					if(strRequest.contains(strRequest2))
						numReply = 1;
					else if(strRequest2.contains(strRequest))
						numReply = -1;
					else
						numReply = 0;				
					objOut.writeObject(new Integer(numReply));					
					break;							
				}
			}while(ch!=0);
			 
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static int factorial(int n)
	{
		if(n==0)
			return 1;
		else
			return n * factorial(n-1);
	}
	
	private static int digitAddition(int n)
	{
		if(n==0)
			return 0;
		else
			return (n%10) + digitAddition(n/10);
	}
	
	private static String reverse(String str)
	{
		char []ch = str.toCharArray();
		char []temp = new char[str.length()];
		String rev = "";
		int i;
		for(i=0;i<str.length();i++)
			temp[i] = ch[str.length() - i - 1];
		rev = new String(temp);
		return rev;
	}
}
