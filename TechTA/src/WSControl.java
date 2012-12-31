


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import model.UserModel;
import model.testModel;

import org.apache.catalina.websocket.WebSocketServlet;
import org.json.*;


import de.roderick.weberknecht.*;


import task.HttpClientRequest;
import task.HttpRequestTask;
import task.HttpWSTask;

import java.net.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class test
 */
@WebServlet("/WSControl")
public class WSControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WSControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		if(request.getParameter("DR")!=null){
			HttpWSTask.DeleteRoom(request.getParameter("DR"));
		}
		if(request.getParameter("DU")!=null){
			HttpWSTask.DeleteUser(request.getParameter("DU"));
		}
		if(request.getParameter("CU")!=null){
			out.println(HttpWSTask.CreateUser());
		}
		if(request.getParameter("CR")!=null){
			out.println(HttpWSTask.CreateRoom());
		}
		out.println("OK!");
		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("{\"POST\":\"SUCCESS\"}");
		out.close();
	}

}
