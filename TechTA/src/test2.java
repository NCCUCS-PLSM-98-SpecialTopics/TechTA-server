


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

import com.google.gson.Gson;


import de.roderick.weberknecht.*;


import task.HttpClientRequest;
import task.HttpRequestTask;
import task.WSClient;

import java.net.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class test
 */
@WebServlet("/test2")
public class test2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		//WSClient.getInstance();
		Map map = new HashMap();
		Map mapinside = new HashMap();
		mapinside.put("test", "testvalue");
		map.put("type", "class");
		map.put("clid", "1234");
		map.put("map", mapinside);
		String jsonmsg = (new Gson()).toJson(map);
		Map map2 = (new Gson()).fromJson(jsonmsg, Map.class);
		
		out.println(map2.toString());
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
