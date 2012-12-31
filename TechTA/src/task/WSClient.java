package task;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import model.MessageModel;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.gson.Gson;
import com.sun.corba.se.pept.protocol.MessageMediator;

/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
public class WSClient extends WebSocketClient {
	
	static WSClient c = null;
	
	public static WSClient getInstance() {
		if (c == null) {
			try {
				c = new WSClient( new URI( TAConfig.WSURLPolling ), new Draft_10());
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
		String json = new Gson().toJson(map);
		this.send(json);
		
	}

	@Override
	public void onOpen( ServerHandshake handshakedata ) {
		System.out.println( "opened connection" );
		

		Map<String,String> map = new HashMap<String,String>();
		map.put("command", "login");
		map.put("user", TAConfig.CHATID);
		String json = (new Gson()).toJson(map);
		this.send(json);
		
		// if you pan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
	}

	@Override
	public void onMessage( String message ) {
		System.out.println( "received: " + message );
		try {
			JSONObject msg = new JSONObject(message);
			System.out.println( "received data: " + msg.get("data").toString());
			JSONObject data = new JSONObject(msg.get("data").toString());
			String type = data.get("type").toString();
			String account = data.get("account").toString();
			if(type.equals("message")){
				String content = data.get("content").toString();
				String clid = data.get("clid").toString();
				MessageModel model = new MessageModel(null, content, clid, account,null);
				dbTask.getInstance().AddMessage(model);
				
			}else if (type.equals("answer")){
			
				String qid = data.get("qid").toString();
				String answer = data.get("content").toString();
				dbTask.getInstance().AddTakeQuiz(qid, account, answer);
			}
			

			
			
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