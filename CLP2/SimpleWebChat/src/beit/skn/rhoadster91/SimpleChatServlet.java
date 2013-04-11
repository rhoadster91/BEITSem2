package beit.skn.rhoadster91;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SimpleChatServlet
 */
public class SimpleChatServlet extends HttpServlet {
	private String id;
	private String msg, req; 
	private static final long serialVersionUID = 1L;
    private static ArrayList<String> chatArrayList;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SimpleChatServlet() {
    	super();
    	chatArrayList = new ArrayList<String>();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		req = request.getParameter("type");
		if(req.contentEquals("login"))
		{
			id = request.getParameter("id");	
			request.getSession().setAttribute("id", id);
			response.sendRedirect("chatpage.jsp");
		}
		else if(req.contentEquals("msg"))
		{
			msg = request.getParameter("chat");
			id = (String)request.getSession().getAttribute("id");
			chatArrayList.add(new String("<b>" + id + "</b>: " + msg));
			PrintWriter pw = response.getWriter();
			Iterator<String> it = chatArrayList.iterator();
			while(it.hasNext())
			{
				pw.write(it.next());
				pw.write("<br>");
			}
			pw.write("<p><a href = \"chatpage.jsp\">Go back</a>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
