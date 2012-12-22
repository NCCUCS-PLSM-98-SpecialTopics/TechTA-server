


//////////////////////////////////Share Global/////////////////////////////////////////////
var Request = function(path,method,data,callback){
	$.ajax({
		url: path,
		data: data,
		type: method,
		dataType: "json",
		success: callback,
		error: function(e){
				alert("ERROR : "+e.responseText)
		},
	});	
}

var MyCourses;
var NowCourse;
var MyClasses;
var NowClass;
var IsTeacher = null; // not null is teacher  class="teacherFunc"

////////////////////////////////////Main menu Contral//////////////////////////////////////

$("#homepageBtn").click(function(){
	//$("#course").addClass("animated fadeInDown");
		$("#course").hide();
		$("#banner").show();
})

$("#courseBtn").click(
	function(){
		$("#course").show();
		$("#banner").hide();
	}
)

///////////////////////////////Event Control///////////////////////////////////////////



////////////////////////////////////////////////////////////////////////////////////////
var refershCourse  = function(){
	Request("api/GetCourse","GET",null,function(info){
		$("#courseContainer").html("");
		MyCourses = info;
		
		for(var num in MyCourses){
			var node = "<div class='box course' datanum="+num+"><h2>"+MyCourses[num].name+"</h2></div>";
			$(node).appendTo("#courseContainer");
		}
		
		//老師增加課程
		var node = "<div class='box addCourseBtn teacherFunc' datanum="+num+"><h2>+新增課程</h2></div>";
			$(node).appendTo("#courseContainer");	
	});
}



var init = function(){
	//清空course
	$("#courseContainer").html("");
	
	$("#courseContainer").show();
	$("#addcourse").hide();
	$("#addclass").hide();
	$("#class").hide();
	
	//取得UserInfo
	/*Request("api/TestMe","GET",null,function(info){
			alert("me:"+info.me);
		});*/
	
	//取得UserInfo
	Request("api/GetAccountInfo","GET",null,function(info){
			$("#UserNameSpan").html(info.name);
			if(info.role == "teacher"){IsTeacher = true;}
			if(IsTeacher){
				$(".teacherFunc").show();
				$(".nonteacherFunc").hide();
			}
			
			
		});
	//開啟teacher功能
	

	
	
	
	//取得課程
	Request("api/GetCourse","GET",null,function(info){
		MyCourses = info;
		for(var num in MyCourses){
			var node = "<div class='box course' datanum="+num+"><h2>"+MyCourses[num].name+"</h2></div>";
			$(node).appendTo("#courseContainer");
		}
		
		//老師增加課程
		var node = "<div class='box addCourseBtn teacherFunc' datanum="+num+"><h2>+新增課程</h2></div>";
			$(node).appendTo("#courseContainer");
		
		
		//CLICK 增加課程
		$("#courseContainer .addCourseBtn").click(function(){
				$("#addcourse").show();
				$("#addcourse form").submit(function(target){
					var currentFrom = target.currentTarget;  //抓資料
					var data = {
						name : currentFrom.name.value,
						year : currentFrom.year.value,
						semester : currentFrom.semester.value,
					}
					
					Request("api/AddCourse","GET",data,function(info){ //發request
						if(info.result == 0){
								alert("完成");
								refershCourse();	
						}
						if(info.error){alert("ERROR:"+info.error);}
						
					});
					
					$(currentFrom).each(function(){ //清空資料
						this.reset();
					});
					$("#addcourse").hide(); //隱藏表單
					
					return false;
				})
		})
		
		
	});
}








/******************Add Class & Course Click***********************/

$('#courseContainer .box.course').click(function(target){ 
     //$(target).index();
	 var num = $(target).attr("datanum");
	 MyClasses = MyCourses[num].classes;
	 NowCourse = MyCourses[num];
	 //hide all others
	 for(var i in MyCourses){
		if(i==num)continue;
		$(".box.course[datanum="+i+"]").hide();
	 }
	 
	 
	for(var num in MyCourses){
		var node = "<div class='box class' datanum="+num+"><h2>"+MyCourses[num].name+"</h2></div>";
		$(node).appendTo("#courseContainer");
	}
	 
	 
	 //老師增加課堂
	var node = "<div class='box addClassBtn teacherFunc' datanum="+num+"><h2>+新增課堂</h2></div>";
		$(node).appendTo("#courseContainer");
	
	$("#courseContainer .addClassBtn").click(function(){
			$("#addclass").show();
			$("#addclass form").submit(function(target){
					var currentFrom = target.currentTarget;  //抓資料
					var data = {
						name : currentFrom.name.value,
						week : currentFrom.week.value,
						coid : NowCourse.coid,
					}
					
					Request("api/AddClass","GET",data,function(info){ //發request
						if(info.result == 0){alert("完成");}
						if(info.error){alert("ERROR:"+info.error);}
					});
					
					$(currentFrom).each(function(){ //清空資料
						this.reset();
					});
					$("#addclass").hide(); //隱藏表單
					refershCourse();
					return false;
				})
		})
	
})

$('#courseContainer .box.class').click(function(target){ 
    
	 var num = $(target).attr("datanum");
	 NowClass = MyClasses[num];
	 
	 //hide all others course and classes
	 $(".box").hide();
	 
	 
	 //go to class
	  $("#courseContainer").hide();
	  $("#class").show();
	  ClassInit();
	  
})



/*************** Class *************/

var ClassInit = function(){
	//抓message資料
	Request("api/GetMessageByClass","GET",{clid: NowClass.clid},function(msgs){
		
		for(var num in msgs){
			var node = "<p class='box msg' datanum="+num+">"+msg[num].account+" : "+msg[num].content+"</p>";
			$(node).appendTo("#messagebox");
		}
	});
	
	//開啟quiz 的websocket
	//************************
		
}

$('.classfuncLink').click(function(target){
	$("#class .content .classFunc").hide();
	
	var testTarget = $(target);
	var divToShow = $(target.currentTarget).attr("linkDiv");
	$(divToShow).show();
})



init();


