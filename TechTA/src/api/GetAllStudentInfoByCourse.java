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

import org.json.JSONObject;

import com.google.gson.Gson;
import com.sun.corba.se.pept.protocol.MessageMediator;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import task.TATool;
import task.dbTask;

import model.CourseModel;
import model.MessageModel;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/GetAllStudentInfoByCourse")
public class GetAllStudentInfoByCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllStudentInfoByCourse() {
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
		
		
		if(!TATool.CheckPerem(new String[]{"coid"}, request, out)){return;}
		String coid  = TATool.utf8Perem(request, "coid");
		
		
		int resultNumber = -1;
		
		List<Map<String, String>> list = dbTask.getInstance().GetAllStudentInfoByCourse(coid);
		
		if(list != null){
			resultNumber= 0;
			int totalquiz = -1; //為了要取得最大值
			
			for(Map<String, String> map : list){
				int num = Integer.parseInt(map.get("TQ"));
				if(num > totalquiz)totalquiz = num;
			}
			for(Map<String, String> map : list){
				Integer.getInteger(map.put("TQ",String.valueOf(totalquiz)));
			}
			
		}else{
			resultNumber = 1;
		}
		
		
		Map map = new HashMap<>();
		map.put("result", resultNumber);
		map.put("list", list);
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		
		out.println(json);
		
        /*end coding*/
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
