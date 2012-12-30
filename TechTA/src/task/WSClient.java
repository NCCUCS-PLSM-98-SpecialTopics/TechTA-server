package task;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.gson.Gson;

/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
public class WSClient extends WebSocketClient {
	static String WSURL = "ws://dream.cs.nccu.edu.tw:8001/websocket/polling";
	static String CHATID = "user_10b3089ab61b46cd99ee6d4eda5c6b4b"; //server用來聽room的id
	static WSClient c = null;
	
	public static WSClient getInstance() {
		if (c == null) {
			try {
				c = new WSClient( new URI( WSURL ), new Draft_10());
			} catch (URISyntaxException e) {
				return null;
			}
			c.connect();
		}
		return c;
	}

	public WSClient( URI serverUri , Draft draft) {
		super( serverUri, draft );
	}	

	public WSClient( URI serverURI ) {
		super( serverURI );
	}
	
	public void SendMsg(String msg, String roomid){
		Map<String,String> map = new HashMap<String,String>();
		map.put("command", "room");
		map.put("room", roomid);
		map.put("msg", msg);
	}

	@Override
	public void onOpen( ServerHandshake handshakedata ) {
		System.out.println( "opened connection" );
		

		Map<String,String> map = new HashMap<String,String>();
		map.put("command", "login");
		map.put("user", CHATID);
		String json = (new Gson()).toJson(map);
		this.send(json);
		
		// if you pan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
	}

	@Override
	public void onMessage( String message ) {
		System.out.println( "received: " + message );
		try {
			JSONObject msg = new JSONObject(message);
			System.out.println( "msg.getJSONArray('data'): " + msg.getJSONArray("data").toString());	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onClose( int code, String reason, boolean remote ) {
		// The codecodes are documented in class org.java_websocket.framing.CloseFrame
		System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) );
	}

	@Override
	public void onError( Exception ex ) {
		ex.printStackTrace();
		// if the error is fatal then onClose will be called additionally
	}



}