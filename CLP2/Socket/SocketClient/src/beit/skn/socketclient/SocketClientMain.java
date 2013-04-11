package beit.skn.socketclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClientMain
{
	static Socket socket;
	static ObjectInputStream objIn;
	static ObjectOutputStream objOut;
	
	public static void main(String []args)
	{
		int ch = 0, sch = 0;
		int numRequest, numReply;
		String strRequest, strRequest2, strReply;
		Scanner sc;
		System.out.println("Connecting to server...");
		try 
		{
			socket = new Socket("localhost", 9456);
			objIn = new ObjectInputStream(socket.getInputStream());
			objOut = new ObjectOutputStream(socket.getOutputStream());
			do
			{
				System.out.println("Select operation:");
				System.out.println("1. Digit addition");
				System.out.println("2. Factorial");
				System.out.println("3. String manipulation");
				System.out.println("0. Exit");
				sc = new Scanner(System.in);
				ch = sc.nextInt();
				switch(ch)
				{
				case 1:
					objOut.writeObject(new Integer(1));
					System.out.println("Enter number: ");
					numRequest = sc.nextInt();
					objOut.writeObject(new Integer(numRequest));
					System.out.println("Waiting for reply...");
					numReply = ((Integer)objIn.readObject()).intValue();
					System.out.println("Answer is " + numReply + ".");
					break;
					
				case 2:
					objOut.writeObject(new Integer(2));
					System.out.println("Enter number: ");
					numRequest = sc.nextInt();
					objOut.writeObject(new Integer(numRequest));
					System.out.println("Waiting for reply...");
					numReply = ((Integer)objIn.readObject()).intValue();
					System.out.println("Answer is " + numReply + ".");					
					break;
					
				case 3:
					do
					{
						System.out.println("Select string operation:");
						System.out.println("1. Length");
						System.out.println("2. Compare");
						System.out.println("3. Concatenation");
						System.out.println("4. Palindrome");
						System.out.println("5. Substring");												
						System.out.println("0. Exit");
						sc = new Scanner(System.in);
						sch = sc.nextInt();
						switch(sch)
						{
						case 1:
							objOut.writeObject(new Integer(31));
							System.out.println("Enter string: ");
							sc = new Scanner(System.in);
							strRequest = sc.nextLine();
							objOut.writeObject(strRequest);
							System.out.println("Waiting for reply...");
							numReply = ((Integer)objIn.readObject()).intValue();
							System.out.println("Answer is " + numReply + ".");							
							break;
							
						case 2:
							objOut.writeObject(new Integer(32));
							System.out.println("Enter first string: ");
							sc = new Scanner(System.in);
							strRequest = sc.nextLine();
							System.out.println("Enter second string: ");
							sc = new Scanner(System.in);
							strRequest2 = sc.nextLine();
							objOut.writeObject(strRequest);
							objOut.writeObject(strRequest2);
							System.out.println("Waiting for reply...");							
							numReply = ((Integer)objIn.readObject()).intValue();
							if(numReply==1)
								System.out.println("They are equal.");
							else
								System.out.println("They are not equal.");
							
							break;
							
						case 3:
							objOut.writeObject(new Integer(33));
							System.out.println("Enter first string: ");
							sc = new Scanner(System.in);
							strRequest = sc.nextLine();
							System.out.println("Enter second string: ");
							sc = new Scanner(System.in);
							strRequest2 = sc.nextLine();
							objOut.writeObject(strRequest);
							objOut.writeObject(strRequest2);
							System.out.println("Waiting for reply...");							
							strReply = (String)objIn.readObject();
							System.out.println("Concatenation is " + strReply + ".");
							break;
						
						case 4:
							objOut.writeObject(new Integer(34));
							System.out.println("Enter string: ");
							sc = new Scanner(System.in);
							strRequest = sc.nextLine();
							objOut.writeObject(strRequest);
							System.out.println("Waiting for reply...");
							numReply = ((Integer)objIn.readObject()).intValue();
							if(numReply==1)
								System.out.println("It is a palindrome.");
							else
								System.out.println("It is not a palindrome.");
							break;
							
						case 5:
							objOut.writeObject(new Integer(35));
							System.out.println("Enter first string: ");
							sc = new Scanner(System.in);
							strRequest = sc.nextLine();
							System.out.println("Enter second string: ");
							sc = new Scanner(System.in);
							strRequest2 = sc.nextLine();
							objOut.writeObject(strRequest);
							objOut.writeObject(strRequest2);
							System.out.println("Waiting for reply...");							
							numReply = ((Integer)objIn.readObject()).intValue();
							if(numReply==2)
								System.out.println("They are equal.");
							else if(numReply==1)
								System.out.println("Second string is substring of first string.");
							else if(numReply==-1)
								System.out.println("First string is substring of second string.");
							else
								System.out.println("No substrings.");
							break;
							
						case 0:
							break;
							
						default:
							System.out.println("Invalid option.");
							break;						
						}
					}while(sch!=0);
					break;
					
				case 0:
					objOut.writeObject(new Integer(0));					
					System.out.println("Good bye.");
					break;
				
				default:
					System.out.println("Invalid option.");
					break;				
				}
			} while(ch!=0);
		}
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				socket.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
