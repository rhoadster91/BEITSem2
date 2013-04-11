package beit.skn.rhoadster91;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.PixelGrabber;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class ExtractionMain extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 685095158348007735L;
	static Image image;
	int pixels[];
	int histogramRed[] = new int[256];
	int histogramBlue[] = new int[256];
	int histogramGreen[] = new int[256];
	int imageWidth, imageHeight;
	boolean isProcessing = false;
	JTextPane textPane;
	JScrollPane scrollPane;
	JButton cmd;
	
	SimpleAttributeSet red = new SimpleAttributeSet();
	SimpleAttributeSet blue = new SimpleAttributeSet();
	SimpleAttributeSet green = new SimpleAttributeSet();
	
    Document doc;
	
	ExtractionMain()
	{
		super("Histogram");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 900, 500);
		setVisible(true);
		StyleConstants.setFontSize(red, 14);
	    StyleConstants.setForeground(red, Color.red);
	    StyleConstants.setFontSize(green, 14);
	    StyleConstants.setForeground(green, Color.green);
	    StyleConstants.setFontSize(blue, 14);
	    StyleConstants.setForeground(blue, Color.blue);
	    setLayout(null);
	    textPane = new JTextPane();
	    scrollPane = new JScrollPane(textPane);	    
	    scrollPane.setBounds(220, 0, 260, 200);
	    cmd = new JButton("Scan");
	    cmd.setBorder(null);
	    cmd.setBounds(0, 205, 50, 30);
	    cmd.addActionListener(new ActionListener()
	    {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				new Thread()
				{
					@Override
					public void run()
					{
						isProcessing = true;
						doc = textPane.getDocument();
						for(int i=0;i<imageWidth*imageHeight;i++)
						{
							int p=pixels[i];
							int r=0xff & (p>>16);
							int g=0xff & (p>>8);
							int b=0xff & (p);
		
		
							try
							{
								String s="  "+r;
								doc.insertString(textPane.getCaretPosition(),s,red);
								s="  "+g;
								doc.insertString(textPane.getCaretPosition(),s,green);
								s="  "+b;
								doc.insertString(textPane.getCaretPosition(),s,blue);
		
							}
							catch(Exception evt)
							{
								evt.printStackTrace();
							}
		
							histogramRed[r]++;
							histogramGreen[g]++;
							histogramBlue[b]++;
							
						}
						repaint();
						
						
					}
				}.start();
			}
	    	
	    });
	    this.add(cmd);
	    cmd.setVisible(true);
	   this.add(scrollPane);	    
	    doc = textPane.getDocument();
		
		
	}
	
	@Override
	public void paint(Graphics gr)
	{
		super.paint(gr);
		if(!isProcessing)
		{
			image = getToolkit().getImage("D:\\img.jpg");
			MediaTracker mt = new MediaTracker(this);
			mt.addImage(image, 0);
			
			try 
			{
				mt.waitForID(0);		
				imageWidth = image.getWidth(null);
				imageHeight = image.getHeight(null);
				pixels = new int[imageWidth * imageHeight];
				PixelGrabber pg = new PixelGrabber(image, 0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);		
				pg.grabPixels();
			}
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
		{
			for(int i=0;i<256;i++)
			{			
				gr.setColor(Color.red);
				int p=(int)(500-histogramRed[i]);
				gr.drawLine(100+i,500,100+i,p);
	
				p=(int)(500-histogramGreen[i]);
				gr.setColor(Color.green);
				gr.drawLine(400+i,500,400+i,p);
	
				p=(int)(500-histogramBlue[i]);
				gr.setColor(Color.blue);
				gr.drawLine(700+i,500,700+i,p);
			}
		}
		gr.drawImage(image, 0, 0, 200, 200, this);
	}
	
	public static void main(String[] args) 
	{
		new ExtractionMain();	
	}
	
	

}
