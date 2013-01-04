


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.UserModel;
import model.testModel;

import org.json.*;

import task.HttpRequestTask;


import com.google.gson.Gson;
import com.sixfire.websocket.WebSocket;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.net.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("{\"GET\":\"SUCCESS\"}");
		Map<String, String[]>  map = request.getParameterMap();
		Map<String, String>  result = new HashMap<String, String> ();
		for(String str : map.keySet()){
			String test = Arrays.toString(map.get(str));
			result.put(str, test.substring(1, test.length()-2));
		}
		out.print(new Gson().toJson(result));
		out.close();

		
		//JSONObject jObject =  new JSONObject(map);
		
		//testModel model = new testModel("name", "1234");
		
		//UserModel model2  =  new UserModel("98703005", "1234", "JACK", null, "", "Student", "123456");
		//JSONObject jObject2 = new JSONObject(model2);
//		
//		try {
//			//WebSocket ws = new WebSocket(new URI("ws://echo.websocket.org"));
//			WebSocket ws = new WebSocket(new URI("ws://140.119.164.163:8001/api/register"),"POST");
//			ws.connect();
//
//			String req = "Hello";
//			ws.send(req);
//			out.println("Send: "+req);
//			String res = ws.recv(); 
//			out.println("GET: "+res);
		
		//String result = HttpRequestTask.execute(new URL("http://140.119.164.163:8001/api/register"),RequestMethod.POST);
		

		
		//out.println(result);
		
		//out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.println("{\"POST\":\"SUCCESS\"}");
		Map<String, String[]>  map = request.getParameterMap();
		Map<String, String>  result = new HashMap<String, String> ();
		for(String str : map.keySet()){
			String test = Arrays.toString(map.get(str));
			result.put(str, test.substring(1, test.length()-2));
		}
		out.print(new Gson().toJson(result));
		out.close();
	}

}
