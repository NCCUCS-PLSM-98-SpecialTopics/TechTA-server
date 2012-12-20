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
import com.sun.corba.se.pept.protocol.MessageMediator;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import task.TATool;
import task.dbTask;

import model.CourseModel;
import model.MessageModel;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/GetMessageByClass")
public class GetMessageByClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMessageByClass() {
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
		String clid ="";
		
		if(request.getParameter("clid")!=null){
			clid = request.getParameter("clid");
		}else{
			out.close();
			return;
		}
		
		List<MessageModel> message = dbTask.getInstance().GetMessageByClassid(clid);
		Gson gson = new Gson();
		String json = gson.toJson(message);
		
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
