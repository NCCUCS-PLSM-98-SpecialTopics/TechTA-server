package task;

import Enum.*;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TATool {
	public static String utf8Perem (HttpServletRequest request, String perem) throws UnsupportedEncodingException{
		
		return new String(request.getParameter(perem).getBytes("ISO-8859-1"),"UTF-8"); 
	}
	public static Boolean CheckLogin (HttpSession session, PrintWriter out){
		
		if(session.getAttribute("account") == null){
			out.println(ErrorCode.NoLogin);
			out.close();
			return false;
		}
		return true;
		
	}

	public static Boolean CheckPerem ( String[]  perems, HttpServletRequest request,PrintWriter out){

		Map<String, String[]> pmap = request.getParameterMap();
		for(String pere : perems ){
			if(!pmap.containsKey(pere)){
				out.println("{ \"error\":\"you lose some parameter\" }");
				out.close();
				return false;
			}
			
		}
		return true;
		
	}
	public String ErrorJson (PrintWriter out){
		return "" ;
		
	}
}
