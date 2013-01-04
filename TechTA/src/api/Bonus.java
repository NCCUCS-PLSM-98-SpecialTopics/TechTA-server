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
import task.WSClient;
import task.dbTask;

import model.ClassModel;
import model.CourseModel;
import model.QuizModel;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/Bonus")   
public class Bonus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bonus() {
        super();
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

		String [] params = new String[]{"clid","account"};
		if(!TATool.CheckPerem(params, request, out)){return;}

		String clid = TATool.utf8Perem(request,"clid");
		ClassModel model = dbTask.getInstance().GetClassByClassid(clid) ;
				
		String account = TATool.utf8Perem(request,"account");
		int resultNumber= -1;
		if(dbTask.getInstance().GetUser(account) == null){resultNumber = 2;} //無此帳號
		else{
			resultNumber = dbTask.getInstance().AddBonusByStudentAndCourse(account, model.getClid(), 1);
		}
		
		if(resultNumber == 0){
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "bonus");
			map.put("clid",clid);
			map.put("cause","課堂加分");
			map.put("student", account);
			String msg = new Gson().toJson(map);
			WSClient.getInstance().SendMsg(msg, model.getRoomid());
		}
		
		
		Map map = new HashMap<>();
		map.put("result", resultNumber);  //0:ok 1:error 2:無此帳號
		
		
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
