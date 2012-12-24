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
@WebServlet("/api/RemoveQuiz")   
public class RemoveQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveQuiz() {
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
		String account = (String) session.getAttribute("account");
		/*Start coding*/

		String [] params = new String[]{"qid"};
		if(!TATool.CheckPerem(params, request, out)){return;}
		
		String qid = TATool.utf8Perem(request,"qid");
		
		int resultNumber = -1;		
		resultNumber = dbTask.getInstance().RemoveQuiz(qid);
		
		Map map = new HashMap<>();
		map.put("result", resultNumber);
		
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
