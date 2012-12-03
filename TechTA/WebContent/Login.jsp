<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>無標題文件</title>
<script type="text/javascript" src="https://www.google.com/jsapi"></script> <!-- 載入JQUERY --> -->
<script> google.load("jquery", "1.6.2"); 
function login(){
	//alert($("account").val());
	/*$.post("api/LoginAccount", {
		account:$("#account").val(),
		password:$("#password").val(),
		}, function (data){
		var jdata = JSON.parse(data);
		if(jdata.result == "2")alert("您的帳號密碼錯誤");
		if(jdata.result == "0")alert("恭喜您登入!");
	});*/
	
	 $.ajax({
	        type: "POST",
	        url: "api/LoginAccount",
	        data: {
	    		account:$("#account").val(),
	    		password:$("#password").val(),
	    	},
	        dataType: "json",
	        success:function (data){
	    		//var jdata = JSON.parse(data);
	    		//if(jdata.result == "2")alert("您的帳號密碼錯誤");
	    		//if(jdata.result == "0")alert("恭喜您登入!");
	    		if(data.result == "2")alert("您的帳號密碼錯誤");
	    	    if(data.result == "0")alert("恭喜您登入!");
	    	},
	        error:function(thrownError){
	          alert(thrownError.responseText);
	        }
      }); 
	 
	return false;
}


</script>
</head>

<body>



<div style=" text-align:center">
<h1>Tech TA</h1>
<form id="form1" name="form1" method="post" action="api/LoginAccount" onsubmit="return login();">
  <p>請使用NCCU電子郵件帳號登入</p>
  <p>帳號：　
    <input type="text" name="account" id="account" />
    
  </p>
  <p>密碼：　<input type="password"  id="password"/></p>
  <p>&nbsp;</p>
  <p>
    <input type="submit" name="button" id="button" value="送出" />
  </p>
</form>
<p>&nbsp;</p>
</div>
</body>
</html>
    