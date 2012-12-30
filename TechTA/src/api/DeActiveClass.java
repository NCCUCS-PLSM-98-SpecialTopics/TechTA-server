package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.google.gson.Gson;

import task.HttpWSTask;
import task.TATool;
import task.WSClient;
import task.dbTask;

import model.ClassModel;
import model.CourseModel;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/DeActiveClass")  
public class DeActiveClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeActiveClass() {
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
		int resultcode = -1;
		//驗證登入狀態
		if(!TATool.CheckLogin(session,out)) {return;}
		
		/*Start coding*/
		
		if(!TATool.CheckPerem(new String[]{"clid"}, request, out)){return;}
		String clid  = TATool.utf8Perem(request, "clid");
		ClassModel model= dbTask.getInstance().GetClassByClassid(clid);
		
		
		//Create and add all user to room
		if(model.getRoomid()!=null){
			String result = HttpWSTask.DeleteRoom(model.getRoomid());
		}

		dbTask.getInstance().DeActiveClass(clid);
		
		if(model.getRoomid()!=null){
			//發出WS 開啟了一門課
			Map<String,String> map = new HashMap<String,String>();
			map.put("type", "class");
			map.put("clid", clid);
			String jsonmsg = (new Gson()).toJson(map);
			WSClient.getInstance().SendMsg(jsonmsg, model.getRoomid());
		}
		
		resultcode = 0;
		Map map = new HashMap<>();
		map.put("result", resultcode);  //0:OK 

        /*end coding*/
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
