package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserModel;

import task.MD5;
import task.TATool;
import task.dbTask;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/UpdateMyAccount")
public class UpdateMyAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMyAccount() {
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
		HttpSession session = request.getSession();
		if(!TATool.CheckLogin(session,out)) {return;}
		String account = (String) session.getAttribute("account");
		
		int returnResult = -1;
		
		if(!TATool.CheckPerem(new String[]{"name","email"}, request, out)){return;}
		
		UserModel model = dbTask.getInstance().GetUser(account);
		
		if(request.getParameter("password") != null){
			model.setPassword(MD5.encode(TATool.utf8Perem(request, "password")));
		}
		model.setName(TATool.utf8Perem(request, "name") );
		model.setEmail(TATool.utf8Perem(request, "email"));
 

		
		if(dbTask.getInstance().GetUser(account) != null){ 
			//update account
			
			int resultUpdate  = dbTask.getInstance().UpdateAccount(model);
			 
			if(resultUpdate == 0){
				returnResult = 0;
			}else{
				returnResult = 1; //db error
			}
			
		}else{     
			returnResult = 2;//no such account
		}
		
		
        out.println("{ \"result\":"+returnResult+" }");
        out.close();
	}

}
