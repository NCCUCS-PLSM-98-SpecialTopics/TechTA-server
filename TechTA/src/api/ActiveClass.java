package api;

import de.roderick.weberknecht.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
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
import javax.xml.ws.WebServiceClient;

import org.json.JSONException;
import org.json.JSONObject;


import com.google.gson.Gson;

import task.HttpWSTask;
import task.TAConfig;
import task.TATool;
import task.WSClient;
import task.dbTask;

import model.ClassModel;
import model.CourseModel;
import model.UserModel;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/ActiveClass")  /*<<<<<<<<<CHANG HERE**/
public class ActiveClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActiveClass() {
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
		
		int result =-1; 
		/*Start coding*/
		
		
		if(!TATool.CheckPerem(new String[]{"clid"}, request, out)){return;}
		String clid  = TATool.utf8Perem(request, "clid");
		
		
		ClassModel model = dbTask.getInstance().GetClassByClassid(clid);
		
		String coid = model.getParentCourseId();
		
		
		//Create and add all user to room
		String roomid = HttpWSTask.CreateRoom();  //新增room
		
		if(roomid != null && !roomid.equals("")){
			//把所有人加入room
			List<UserModel> list = dbTask.getInstance().GetAllUserByCourse(coid);
			for(UserModel user : list){
				HttpWSTask.PutUserToRoom(roomid, user.getChatid());
			}
			//把系統ID加入room
			HttpWSTask.PutUserToRoom(roomid, TAConfig.CHATID);
			
			//啟動課程DB
			dbTask.getInstance().ActiveClass(clid,roomid);
			
			//發出WS 開啟了一門課
			Map<String,String> map = new HashMap<String,String>();
			map.put("type", "class");
			map.put("clid", clid);
			String jsonmsg = (new Gson()).toJson(map);
			WSClient.getInstance().SendMsg(jsonmsg, roomid);

			result = 0;
		}else{
			result = 2; //roomid error
		}
		
		Map map = new HashMap<>();
		map.put("result", result);  //0:OK  2:roomid error

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
