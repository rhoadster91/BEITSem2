package beit.skn.webcrawler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CrawlerMain 
{
	static JFrame myFrame;
	static int x = 10, y = 30;
	static JTextField iptext;
	static JTextArea optext;
	static JScrollPane opscroll;
	public static void main(String[] args)
	{
		myFrame = new JFrame();
		myFrame.setLayout(null);
		myFrame.setBounds(20, 20, 500, 500);
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iptext = new JTextField();
		JButton trace = new JButton("...");
		optext = new JTextArea();
		optext.setEditable(false);
		iptext.setBounds(10, 10, 430, 20);
		trace.setBounds(450, 10, 40, 20);
		myFrame.add(iptext);
		myFrame.add(trace);
		opscroll = new JScrollPane(optext);
		opscroll.setBounds(10, 40, 480, 420);		
		myFrame.add(opscroll);
		myFrame.repaint();
		trace.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					String cmdURL = iptext.getText();
					URL myURL = new URL(cmdURL);
					optext.setText("");
					printOnNextLine("Crawling the URL " + cmdURL);
					BufferedReader in;			
					in = new BufferedReader(new InputStreamReader(myURL.openStream()));
					String inputLine;
					
					while ((inputLine = in.readLine()) != null)
					{
						Pattern pattern1 = Pattern.compile("\"https*://[\\w*.*/*]*\"");
						Matcher matcher = pattern1.matcher(inputLine);
						if(matcher.find())
						{
							String str = matcher.group(0);
							printOnNextLine(str.split("\"")[1]);
						}
					}
						
					in.close();
					printOnNextLine("Crawling complete.");
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}	
			}});
		
		
		
	}
	
	public static void printOnNextLine(String s)
	{
		optext.setText(optext.getText() + "\n" + s);
		myFrame.repaint();
	}
}
