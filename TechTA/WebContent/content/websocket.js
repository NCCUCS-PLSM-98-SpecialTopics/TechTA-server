var WSClient = {
	server_host : "ws://dream.cs.nccu.edu.tw:8001/websocket/polling"
    ,ws : ''
	,user:''
	,onMsgFunc:''
	,init: function(usr,onMsgFunc){
		if(!usr)console.log("[WSClient/init] no usr ");
		
		this.ws = new WebSocket(this.server_host);
		if(onMsgFunc){
			this.onmessage = onMsgFunc;
		}
		if(usr)this.user = usr;
		this.ws.onmessage = this.onmessage;
		this.ws.onclose = this.onclose;
		this.ws.onopen = this.onopen;
		
	}

	,login : function() {
		var data = {
			"command": "login",
			"user": this.user.chatid
		};
		console.log("[Login] " + JSON.stringify(data));
		var jsondata =  JSON.stringify(data);
		WSClient.ws.send(jsondata);
	}
	,onopen: function(){
			WSClient.login();
	}
	,onclose: function(evt) {
			setTimeout(function(){
				WSClient.init();
			},1000);
	}
	,send: function(msg, room) {
		if(!room || !msg)return;
		var data = {
			"command": "room",
			"room": room,
			"msg": msg
		};
		WSClient.ws.send(JSON.stringify(data));
	}
	
	,sendMsg: function(msg, cls) {
		if(!cls || !msg)return;
		var data = {
			"type": "message",
			"clid": cls.clid,
			"account": this.user.account,
			"role": this.user.role,
			"content": msg
		};
		WSClient.send(JSON.stringify(data),cls.roomid);
	}
	,sendAns: function(qid, answer, cls) {
		if(!qid || !answer || !cls)return;
		var data = {
			"type": "answer",
			"clid": cls.clid,
			"qid": qid,
			"account": this.user.account,
			"content": answer
		};
		WSClient.send(JSON.stringify(data),cls.roomid);
	}
	,onmessage :function(e){
            var msg = e.data;
            console.log("[WSClient/onmessage] " + msg);
    }

}