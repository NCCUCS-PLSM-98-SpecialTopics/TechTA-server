package task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import Enum.RMethod;

public class HttpWSTask {
	static String APIKEY = "apikey_8e951f9edd2c41639a3089431ab25c69";
	static String WSURL = "dream.cs.nccu.edu.tw:8001";
	
	
	public static String WS(String path, RMethod method, ArrayList<NameValuePair> pairList ){
		String url ="http://"+WSURL+path;
		url = url.replaceAll("<api_key>", APIKEY);
		String result = null;
		try {
			
			switch (method) {
			case GET : result = HttpClientRequest.Get(url);
				break;
			case POST : result = HttpClientRequest.Post(url, pairList);
				break;	
			case PUT : result = HttpClientRequest.Put(url, pairList);
				break;		
			case DELETE : result = HttpClientRequest.Delete(url);
				break;		
			default:
				break;
			}
		
			
		} catch (IOException e) { e.printStackTrace(); }
		
		return result;
	}
	
	
	public static String CreateRoom(){
		
		return WS("/api/room?apikey=<api_key>",RMethod.POST,null);
	}
	public static String DeleteRoom(String roomid){
		String path = "/api/room/<room_uuid>?apikey=<api_key>";
		path = path.replaceAll("<room_uuid>", roomid);
		return WS(path,RMethod.DELETE,null);
	}
	public static String PutUserToRoom(String roomid,String userid){
		String path = "/api/room/<room_uuid>/<user_uuid>/<room_action>?apikey=<api_key>";
		path = path.replaceAll("<room_uuid>", roomid);
		path = path.replaceAll("<user_uuid>", userid);
		path = path.replaceAll("<room_action>", "add");
		return WS(path,RMethod.PUT,null);
	}

	
	public static String CreateUser(){
		return WS("/api/user?apikey=<api_key>",RMethod.POST,null);
	}
	public static String DeleteUser(String userid){
		String path = "/api/user/<user_uuid>?apikey=<api_key>";
		path = path.replaceAll("<user_uuid>", userid);
		return WS(path,RMethod.DELETE,null);
	}

}
