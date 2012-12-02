
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class hello
 */
@WebServlet("/hello")
public class hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public hello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out = response.getWriter();
         //out.println("{\"Account\":\"98703005\", \"Name\":\"捷克裡\"}");
         /*
         jdbcmysql sql = new jdbcmysql();
         sql.execute(out);
         
         //test.dropTable(); 
         sql.createTable(); 
         sql.insertTable("yku", "12356"); 
         sql.insertTable("yku2", "7890"); 
         sql.SelectTable(); 
         
         */
     
     
		try {
			if(checkpassword.execute("98703005","123456")){
				out.println("The password is correct!");
			}else{
				out.println("The password is Worng!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         //jdbcmysql.execute();
         //jdbc2.execute();
         out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
