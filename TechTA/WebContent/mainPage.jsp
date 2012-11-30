<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="https://www.google.com/jsapi"></script> <!-- 載入JQUERY -->
<script> google.load("jquery", "1.6.2"); </script>
<title>無標題文件</title>
</head>

<script type="text/javascript">

$(document).ready(function() {  
	//alert("i am dog");
	//$("#UserName").html("狗狗");
	$.getJSON("api/GetAccountInfo", {account:"98703005"}, function (data){
		$("#UserName").html(data.name);
	});
	$.getJSON("api/GetCourse", {account:"98703005"}, function (data){
		//$("#CourseName").html(data[0].class[0].title);
		
		for(var i in data){
			//data[i].name;
			$("#CourseName").append("<p>"+data[i].name+"</p>")
		}
		
	});
	
	
});  



</script>

<style type="text/css">
	body{
		margin:0;
	}
	.course{
		background-color: #036;
		color:#FFF;
		width:150px;
		height:150px;
		float:left;
		margin: 10px;
		padding:10px;
	}
</style>

<body>

<div style="text-align:left; background-color:#000; color:#FFF; padding:5px 60px;height:40px">
	<div style="display:block;float:left">
        <span style=" font-size:36px; font-weight:700">Tech TA</span>
         科技教學助理
        <span style="display:inline-block; margin-left:100px ">我的課程</span>
        <span style="display:inline-block; margin-left:40px ">個人資料</span>
    </div>
    <div style="width:200px;display:block;float:right;height:40px">
    	<span id="UserName"></span>您好,   登出
    </div>
</div>

<script>
	temp = new Object();
	temp.id = "001";
	temp.name = "Data Structure";

	for (prop in temp){
		//document.write("<span id="CourseName"></span>");
		
		
	}
	
	//$("#courses").append("<div><h2>計算機概論</h2></div>");
</script>

<div style=" text-align:center">
    <br />

    <div style=" width:600px; height:600px;text-align:left ;  margin:0 auto 0">
    <h1>課程:</h1>
    <div id="courses">
    	<span id="CourseName"></span>
	    <!-- <div class="course" onclick="location.href='class.html'"><h2>計算機概論</h2></div>
	    <div class="course"><h2>計算機程式設計</h2></div>
	    <div class="course"><h2>程式語言</h2></div>
	    <div class="course"><h2>系統程式</h2></div>
	    <div class="course"><h2>人工智慧</h2></div>
	    <div class="course"><h2>計算機組織與結構</h2></div> -->
    </div>
    </div>
    
<p>&nbsp;</p>
</div>

</body>

</html>
