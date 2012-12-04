package api;
import task.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/LoginAccount")
public class LoginAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String username = "";
		String password = "";
		
		HttpSession session = request.getSession();
		/*
		if(session.getAttribute("account") == null){
			out.println("{ \"error\":\"you should login\" }");
			out.close();
			return;
		}*/
		
		
		if(request.getParameter("account")!=null)
		 username = request.getParameter("account").toString();
		if(request.getParameter("password")!=null)
		 password = request.getParameter("password").toString();
		
		if(checkpassword.execute(username, password)){
			
			if(dbTask.getInstance().GetUser(username) != null){
				out.println("{ \"result\":\"0\" }");
			}else{
				out.println("{ \"result\":\"2\" }");
			}
			session.setAttribute("account", username);
		}else{
			out.println("{ \"result\":\"1\" }");
		}
      //out.println("{ \"result\":1 }");
        
        out.close();
	}

}
