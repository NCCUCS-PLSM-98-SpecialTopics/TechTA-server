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
@WebServlet("/api/AddQuizToClass")   
public class AddQuizToClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddQuizToClass() {
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

		String [] params = new String[]{"question","choices","correctanswer","clid"};
		if(!TATool.CheckPerem(params, request, out)){return;}
		


		String qid = null;
		String question = TATool.utf8Perem(request,"question");
		String correctAnswer =TATool.utf8Perem(request,"correctanswer");
		String choice =TATool.utf8Perem(request,"choices");
		String active ="0";
		String clid =TATool.utf8Perem(request,"clid");
		
		Boolean IS_UPDATE =false;
		if(request.getParameter("qid")!=null){
			qid = TATool.utf8Perem(request,"qid");
			IS_UPDATE = true;
		}
		
		QuizModel model = new QuizModel(qid, question, correctAnswer, choice, active, clid,null);
		
		
		int resultNumber = -1;		
		String result = null;
		
		if(IS_UPDATE){
			resultNumber =  dbTask.getInstance().UpdateQuiz(model);
			
		}else{
			result = dbTask.getInstance().AddQuizToClass(model);
			model = dbTask.getInstance().GetQuizByqid(result);
			resultNumber = (result != null)?0:1;
		}
		
		
		Map map = new HashMap<>();
		map.put("result", resultNumber);
		map.put("quiz", model);
		
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
