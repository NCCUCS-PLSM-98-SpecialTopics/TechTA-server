<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>無標題文件</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script> <!-- 載入JQUERY --> 
<script>

//google.load("jquery", "1.6.2"); 
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
	    		if(data.result == "1")$("#LoginAccount").html("您的帳號密碼錯誤");
	    		if(data.result == "2")$("#LoginAccount").html("請申請帳號");
	    	    if(data.result == "0")$("#LoginAccount").html("恭喜您登入!");
	    	},
	        error:function(thrownError){
	        	$("LoginAccount").html(thrownError.responseText);
	        }
      }); 

	return false;
}


$("html").ready(function(){

	$("#GetCourseBtn").click(

		function(){
			$("#GetCourse").html("");
			 $.ajax({
			        type: "GET",
			        url: "api/GetCourse",
			        data: {
			    		account:$("#account").val(),
			    		password:$("#password").val(),
			    	},
			        dataType: "json",
			        success:function (data){

			        	$("#GetCourse").html(JSON.stringify(data));

			    	},
			        error:function(thrownError){
			        	$("#GetCourse").html(thrownError.responseText);
			        }
		      }); 

		}	
	)

	$("#GetAccountBtn").click(

		function(){
			$("#GetAccount").html("");
			 $.ajax({
			        type: "GET",
			        url: "api/GetAccountInfo",
			        data: {
			    		account:$("#account").val(),
			    		password:$("#password").val(),
			    	},
			        dataType: "json",
			        success:function (data){

			        	$("#GetAccount").html(JSON.stringify(data));
			        	$("#U_account").val(data.account);
			        	$("#U_name").val(data.name);
			        	$("#U_email").val(data.email);
			        	$("#U_department").val(data.department);
			        	$("#U_chatid").val(data.chatid);
			    	},
			        error:function(thrownError){
			        	$("#GetAccount").html(thrownError.responseText);
			        }
		      }); 

		}	
	)



	$(".UpdateAccountBtn").click(

		function(){
			$("#UpdateAccount").html("");
			 $.ajax({
			        type: "POST",
			        url: "api/CreateAccount",
			        data: {
			    		account:$("#U_account").val(),
			    		password:$("#U_password").val(),
			    		name:      $("#U_name").val(),
			    		email:     $("#U_email").val(),
						department:$("#U_department").val(),
						role:$("#U_role").val(),

			    	},
			        dataType: "json",
			        success:function (data){

			        	$("#UpdateAccount").html(JSON.stringify(data));
			    	},
			        error:function(thrownError){
			        	$("#UpdateAccount").html(thrownError.responseText);
			        }
		      }); 

		}	
	)

	$("#CLEAR").click(

			function(){
				$("#GetAccount").html(" ");
				$("#GetCourse").html(" ");
				$("#LoginAccount").html(" ");
			}	
		);
		
	$('#testScriptBtn').click(function(){
		 var url = $('#testScriptUrl').val();
		 var method = $('#testScriptMethod').val();
		 var html = $('#testScriptData').val();
		 var data = JSON.parse(html);
		  

		 $.ajax({
		        type: method,
		        url: url,
		        data: data,
		        dataType: "json",
		        success:function (data){
                     $('#testScriptDiv').html(data);
		    	},
		        error:function(thrownError){
		        	 $('#testScriptDiv').html(thrownError.responseText);
		        }
	      }); 

	
		
	});

})


</script>
</head>

<body>


<input type="button" id="CLEAR"  value="CLEAR" />
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

<div style="width:50%; background-color:#578" id="LoginAccount">

</div>

<h1>GetCourse</h1>
<input type="button" id="GetCourseBtn"  value="GetCourse" />
<div style="width:50%; background-color:#578" id="GetCourse">

</div>


<h1>GetAccount</h1><input type="button" id="GetAccountBtn"  value="GetAccount" />
<div style="width:50%; background-color:#578" id="GetAccount">

</div>

<h1>UpdateAccount</h1>

	<form id="form1" name="form1" method="post" action="#" onsubmit="return UpdateAccount();">
	    <p>請使用NCCU電子郵件帳號登入</p>
	  <p>   account：<input type="text" name="account" id="U_account" />   </p>
	  <p>   password：<input type="text" name="password" id="U_password" />   </p>
	  <p>   name：<input type="text" name="name" id="U_name" />   </p>
	  <p>   email：<input type="text" name="email" id="U_email" />   </p>
	  <p>   department：<input type="text" name="department" id="U_department" />   </p>
	  <p>   role：
			  <select name="role"  id="U_role" >
				  <option value="teacher">老師</option>
				  <option value="student">學生</option>
				</select>
 	</p>
	  <p>&nbsp;</p>
	  <p>
	    <input type="button" class="UpdateAccountBtn"  value="UpdateAccount" />
	    <!-- <input type="submit" name="button" id="button" value="送出" /> -->
	  </p>
	</form>



<div style="width:50%; background-color:#578" id="UpdateAccount">
</div>



URL:<input id="testScriptUrl" type="text" value="api/GetAccountInfo" /> <br/>
Method:<input id="testScriptMethod" type="text" value="GET"/><br/>
data:<textarea id="testScriptData"  style="width:50%;">{}</textarea><br/>
<input id="testScriptBtn" type="button" value ="GO!"></input>
<div id="testScriptDiv"  style="width:50%; background-color:#578">test</div>

<p>&nbsp;</p>
</div>
</body>
</html>