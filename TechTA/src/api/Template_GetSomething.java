package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.google.gson.Gson;

import task.dbTask;

import model.CourseModel;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/GetMessageByClass")  /*<<<<<<<<<CHANG HERE**/
public class Template_GetSomething extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Template_GetSomething() {
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
		if(session.getAttribute("account") == null){
			out.println("{ \"error\":\"you should login\" }");
			out.close();
			return;
		}
		String account = (String) session.getAttribute("account");
		/*Start coding*/
		
		
		
		
		
		
		
		
//		
//		List<CourseModel> result = dbTask.getInstance().GetCourseByAccount(account);
//		Gson gson = new Gson();
//		String json = gson.toJson(result);
//		
//		//JSONObject jsonUser = new JSONObject(result);
//        out.println(json);
       
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
