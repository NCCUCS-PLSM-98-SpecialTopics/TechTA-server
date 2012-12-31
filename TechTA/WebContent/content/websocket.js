var WSClient = {
	server_host : "ws://dream.cs.nccu.edu.tw:8001/websocket/polling"
    ,ws : ''
	,user:''
	,init: function(usr,onMsgFunc){
		if(!usr)console.log("[WSClient/init] no usr ");
		
		this.ws = new WebSocket(server_host);
		this.user = usr;
		this.login();
		if(onMsgFunc){
			this.onmessage = onMsgFunc;
		}
		this.ws.onmessage = this.onmessage;
		
	}

	,login : function() {
		var data = {
			"command": "login",
			"user": this.user.chatid
		};
		console.log("[Login] " + JSON.stringify(data));
		ws.send(JSON.stringify(data));
	}
	,send: function(room, msg) {
		var data = {
			"command": "room",
			"room": room,
			"msg": msg
		};
		ws.send(JSON.stringify(data));
	}
	,onmessage = function(e){
            var msg = e.data;
            console.log("[WSClient/onmessage] " + msg);
    }
	user1_ws.onmessage = user1_onmsg;









}