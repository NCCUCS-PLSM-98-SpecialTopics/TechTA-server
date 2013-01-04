package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
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

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import task.TATool;
import task.WSClient;
import task.dbTask;

import model.ClassModel;
import model.CourseModel;
import model.QuizModel;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/ActiveQuiz")   
public class ActiveQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActiveQuiz() {
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

		String [] params = new String[]{"qid","active"};
		if(!TATool.CheckPerem(params, request, out)){return;}
		
		String qid = TATool.utf8Perem(request,"qid");
		Boolean DoActive = (TATool.utf8Perem(request,"active").equals("true"))?true:false;
		
		QuizModel quiz = dbTask.getInstance().GetQuizByqid(qid);
		
		String roomid = dbTask.getInstance().GetClassByClassid(quiz.getClid()).getRoomid();
		int resultNumber = -1;	
		resultNumber = dbTask.getInstance().ActiveQuiz(qid, (DoActive)?"1":"0");
		

		if(DoActive ){
			if(roomid!=null){
				//發出WS 開啟了一個問題
				Map<String,String> map = new HashMap<String,String>();
				map.put("type", "quiz");
				map.put("qid", quiz.getQid());
				map.put("question", quiz.getQuestion());
				map.put("clid", quiz.getClid());
				map.put("choice", quiz.getChoice());
				map.put("correctAnswer", quiz.getCorrectAnswer());
				map.put("active", quiz.getActive());
				 
				
				String jsonmsg = (new Gson()).toJson(map);
				WSClient.getInstance().SendMsg(jsonmsg, roomid);
			}
			else{
				resultNumber = 2; //system error wrong roomid
			}
		}else{
			//發出WS 關閉了一個問題
			Map<String,String> map = new HashMap<String,String>();
			map.put("type", "quizclose");
			map.put("qid", quiz.getQid());
			map.put("clid", quiz.getClid());
			String jsonmsg = (new Gson()).toJson(map);
			WSClient.getInstance().SendMsg(jsonmsg, roomid);
			
		}
		
		
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
