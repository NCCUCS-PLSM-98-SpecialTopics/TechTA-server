package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.tools.Tool;

import org.json.JSONObject;

import com.google.gson.Gson;

import task.TATool;
import task.dbTask;

import model.ClassModel;
import model.CourseModel;
import model.QuizModel;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/AddStudentToCourse")   
public class AddStudentToCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStudentToCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//驗證登入狀態
		if(!TATool.CheckLogin(session,out)) {return;}
		//String account = (String) session.getAttribute("account");
		/*Start coding*/

		String [] params = new String[]{"coid","account"};
		if(!TATool.CheckPerem(params, request, out)){return;}
		


		String coid = TATool.utf8Perem(request,"coid");
		String account =TATool.utf8Perem(request,"account");
		
		int resultNumber = dbTask.getInstance().CheckUserToCourse(coid, account);
		
		
		if(resultNumber == 0){
			int result = dbTask.getInstance().AddUserToCourse(account, coid);
			if(result == 0){
				resultNumber =0;
			}else{
				resultNumber= 4; //system error
			}
			
		}
		Map map = new HashMap<>();
		map.put("result", resultNumber);  //0:ok 1:already in this course  2:no account 4:error
		
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(map);
		out.println(jsonString);
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
