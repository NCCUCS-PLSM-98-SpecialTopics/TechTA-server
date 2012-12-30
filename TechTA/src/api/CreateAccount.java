package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Enum.RMethod;

import model.UserModel;

import task.HttpRequestTask;
import task.HttpWSTask;
import task.MD5;
import task.TATool;
import task.dbTask;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/CreateAccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccount() {
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
		
		int returnResult = -1;
		
		if(!TATool.CheckPerem(new String[]{"account","password","name","email","department"}, request, out)){return;}
			
		
		String account =TATool.utf8Perem(request, "account");
		String password =MD5.encode(TATool.utf8Perem(request, "password"));
		String name =TATool.utf8Perem(request, "name");
		String email =TATool.utf8Perem(request, "email");
		String department =TATool.utf8Perem(request, "department");
		String role ="student";
		String chatid ="";
		if(request.getParameter("role")!=null)
			role = request.getParameter("role").toString();
		
		UserModel model = new UserModel(account, password, name, email, department, role, chatid);
		
		
		if(dbTask.getInstance().GetUser(account) != null){ 
			returnResult = 2; //帳號已被使用
		}else{     
			
			//註冊chatID----------
			String userchatid = HttpWSTask.CreateUser();
			if(userchatid==null){
				returnResult = 3; //get chat key error
				
			}else{
				model.setChatid(userchatid);
				int resultCreate = dbTask.getInstance().CreateAccount(model);
				returnResult = resultCreate;
			}
		}
		
		
        out.println("{ \"result\":"+returnResult+" }");
        out.close();
	}

}
