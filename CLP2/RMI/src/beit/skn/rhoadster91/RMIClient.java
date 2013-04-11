package beit.skn.rhoadster91;

import java.rmi.Naming;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class RMIClient 
{
	public static void main(String []args)
	{
		try
		{
			String url = "rmi://" + "localhost" + "/GKRMIServer";
			RMIIntf rmi = (RMIIntf)Naming.lookup(url);
			HashMap<Integer, String> hm = null;
			int ch;
			int rno;
			String name;
			do
			{
				System.out.println("Which operation would you like to perform??");
				System.out.println("1. Display table");
				System.out.println("2. Insert");
				System.out.println("3. Update");
				System.out.println("4. Delete");
				System.out.println("5. Exit");
				Scanner sc = new Scanner(System.in);
				ch = sc.nextInt();
				switch(ch)
				{
					case 1:
						hm = rmi.select();
						
						Set<Entry<Integer, String>> set = hm.entrySet();
						Iterator<Entry<Integer, String>> iterator = set.iterator();
						while(iterator.hasNext())
						{
							Map.Entry<Integer, String> mapEntry = (Map.Entry<Integer, String>)iterator.next();
							System.out.println(mapEntry.getKey() + ": " + mapEntry.getValue());
						}
						break;
					case 2:
						System.out.println("Enter roll number:");
						sc = new Scanner(System.in);
						rno = sc.nextInt();
						System.out.println("Enter name:");
						sc = new Scanner(System.in);						
						name = sc.nextLine();
						rmi.insert(rno, name);
						break;
					case 3:
						System.out.println("Enter roll number whose name you want to update:");
						sc = new Scanner(System.in);
						rno = sc.nextInt();
						System.out.println("Enter new name:");
						sc = new Scanner(System.in);						
						name = sc.nextLine();
						rmi.update(rno, name);
						break;
					case 4:
						System.out.println("Enter roll number whose name you want to delete:");
						sc = new Scanner(System.in);
						rno = sc.nextInt();
						rmi.delete(rno);
						break;
					case 5:
						break;
					default:
						break;
					
				}
			} while(ch!=5);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
