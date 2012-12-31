package task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import Enum.RMethod;

public class HttpWSTask {
	
	
	
	public static String WS(String path, RMethod method, ArrayList<NameValuePair> pairList ){
		String url ="http://"+TAConfig.WSURL+path;
		url = url.replaceAll("<api_key>", TAConfig.APIKEY);
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
		String resutlt =  WS("/api/room?apikey=<api_key>",RMethod.POST,null);
		String roomid = null;
		if(resutlt!=null){
			 Map<String,String> map = new Gson().fromJson(resutlt,  new TypeToken<Map<String,String>>(){}.getType());
			 if(map.get("status")!=null &&  map.get("status").equals("ok")){
				 roomid=map.get("room_id");
			 }
			
		}
		return roomid;
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
		String resutlt =   WS("/api/user?apikey=<api_key>",RMethod.POST,null);
		String user_id = null;
		if(resutlt!=null){
			 Map<String,String> map = new Gson().fromJson(resutlt,  new TypeToken<Map<String,String>>(){}.getType());
			 if(map.get("status")!=null &&  map.get("status").equals("ok")){
				 user_id=map.get("user_id");
			 }
			
		}
		return user_id;
	}
	public static String DeleteUser(String userid){
		String path = "/api/user/<user_uuid>?apikey=<api_key>";
		path = path.replaceAll("<user_uuid>", userid);
		return WS(path,RMethod.DELETE,null);
	}

}
