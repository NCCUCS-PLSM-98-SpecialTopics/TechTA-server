package api;

import java.io.IOException;
import java.io.PrintWriter;
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

import task.TATool;
import task.dbTask;

import model.ClassModel;
import model.CourseModel;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/AddClass")   
public class AddClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddClass() {
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

		
		 Map<String, String[]> pmap = request.getParameterMap();
		if(!pmap.containsKey("name")&&!pmap.containsKey("week")&&!pmap.containsKey("coid")){
			out.println("{ \"error\":\"you lose some parameter\" }");
			out.close();
			return;
		}
		
		String name = TATool.utf8Perem(request,"name");
		String week = TATool.utf8Perem(request,"week");
		String coid = TATool.utf8Perem(request,"coid");
		String active  = "0";
		
		ClassModel model = new ClassModel(null, name, week, active, coid, null, null);
		
		int resultNumber = -1;
		String result = dbTask.getInstance().CreateClassFromCourse(model);
		
		if(result != null){
			resultNumber = 0;
		}
		
		
		out.println("{ \"result\":\""+resultNumber+"\" }");
		
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
