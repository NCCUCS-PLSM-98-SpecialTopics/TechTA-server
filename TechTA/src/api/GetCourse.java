package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginAccount
 */
@WebServlet("/api/GetCourse")
public class GetCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        out.println("{[\n {\n   \"cid\":\"000001\",\n   \"name\":\"�p�������\",\n   \"class\":[\n	  {\n		   \"clid\":\"001\",\n		   \"title\":\"�Ĥ@���:²��\",\n		   \"rid\":\"123456\"\n	  },\n	  {\n		   \"clid\":\"002\",\n		   \"title\":\"�ĤG���:���v\",\n		   \"rid\":\"123456\"\n	  }\n   ]\n },\n {\n   \"cid\":\"000002\",\n   \"name\":\"�p����{���]�p\",\n   \"class\":[\n	  {\n		   \"clid\":\"001\",\n		   \"title\":\"�Ĥ@���:�Һ�����\",\n		   \"rid\":\"123456\"\n	  },\n	  {\n		   \"clid\":\"002\",\n		   \"title\":\"�ĤG���:�y�����v\",\n		   \"rid\":\"123456\"\n	  }\n   ]\n }\n]}");
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
