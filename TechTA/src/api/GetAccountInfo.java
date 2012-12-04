package api;

import java.io.IOException;
import java.io.PrintWriter;

import model.UserModel;
import model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import task.dbTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/GetAccountInfo")
public class GetAccountInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAccountInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String account = "";
		
		if(request.getParameter("account")!=null){
			account = request.getParameter("account");
		}
				
		UserModel user = dbTask.getInstance().GetUser(account);
		
		JSONObject jsonUser = new JSONObject(user);
		jsonUser.remove("password");
        out.println(jsonUser.toString());
        out.close();  
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
