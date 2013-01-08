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
import model.MessageModel;
import model.QuizModel;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/MessageBonus")   
public class MessageBonus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageBonus() {
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

		String [] params = new String[]{"mid"};
		if(!TATool.CheckPerem(params, request, out)){return;}

		String mid = TATool.utf8Perem(request,"mid");
		
		Boolean minus = false;
		if(request.getParameter("minus")!=null && !request.getParameter("minus").toString().equals("")){
			minus=true;
		}
		int resultNumber = dbTask.getInstance().AddMessageBonus(mid, minus);
		
		MessageModel msgMessageModel = dbTask.getInstance().GetMessageByMid(mid);
		
		if(resultNumber==0 && !minus){
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "bonus");
			map.put("clid",msgMessageModel.getClid());
			map.put("cause","訊息加分");
			map.put("student", msgMessageModel.getAccount());
			String msg = new Gson().toJson(map);

			ClassModel classModel = dbTask.getInstance().GetClassByClassid(msgMessageModel.getClid());
			WSClient.getInstance().SendMsg(msg, classModel.getRoomid());
		}
		
		Map map = new HashMap<>();
		map.put("result", resultNumber);  //0:ok 1:error
		
		
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
