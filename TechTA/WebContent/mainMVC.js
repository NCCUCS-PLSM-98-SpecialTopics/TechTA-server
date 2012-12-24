// JavaScript Document
jQuery(document).ready(function() {


//////////////////////////////////Share Global/////////////////////////////////////////////
var RequestObj = new Array(); ;
var RequestOccupied =false;

var Request = function(path,method,data,callback){

	if(RequestObj.length == 0){
		RequestObj[RequestObj.length] = {
			path:path,
			method:method,
			data:data,
			callback:callback
		}
		DoRequest(path,method,data,callback);
	}else{
		RequestObj[RequestObj.length] = {
			path:path,
			method:method,
			data:data,
			callback:callback
		}
	}
	

}

var DoRequest = function(path,method,data,callback){
		$.ajax({
			url: path,
			data: data,
			type: method,
			dataType: "json",
			success: function(result){
					RequestObj.splice(0,1);
					if(RequestObj.length != 0){
						var a = RequestObj[0];
						DoRequest(a.path,a.method,a.data,a.callback);
					}
					timer = null;
					if(result&&result.error){
						if(result.code =="100"){alert("請重新登入!"); return;}
					}
					callback(result);
			},
			error: function(e){
					
					timer = null;
					alert("ERROR : "+e.responseText)
			},
		});	
}

var MyCourses;
var NowCourseIndex;
var NowCourse;

var MyClasses;
var NowClassIndex;
var NowClass;

var MyQuizzes;
var MyStudents;

var IsTeacher = false; // not null is teacher  class="teacherFunc"

///////////////////////////////Event Control///////////////////////////////////////////
var TA = {
	event: function(){	
			//main menu
			$("#courseBtn").live("click",this.CourseBtnClick);
			$("#homepageBtn").live("click",this.HomepageBtnClick);
			$("#LogoutBtn").live("click",this.LogoutClick);
			
			//course
			$("#courseContainer .addCourseBtn").live("click",this.AddCourseArea);
			$("#courseContainer .addClassBtn").live("click",this.AddClassArea);
			$('#courseContainer .box.course').live("click",this.CourseOnClick);
			$('#courseContainer .box.class').live("click",this.ClassOnClick);
			
			//class
			$('.classfuncLink').live("click",this.ClassFuncHandler);
			$('.classActiveLink').live("click",this.ClassActiveHandler);
			$('#quizContainer form').live("submit",this.SubmitQuiz);
			$('#AddQuestionBtn').live("click",this.AddQuizForm);
			$('#quizContainer .ModifyQuiz').live("click",this.ModifyQuizForm);
			$('#quizContainer .RemoveQuiz').live("click",this.RemoveQuizForm);
			$('#quizContainer .ActiveQuiz').live("click",this.ActiveQuizForm);
			$('#quizContainer .DeActiveQuiz').live("click",this.DeActiveQuizForm);
			$('#AddStudentBtn').live("click",this.AddStudentClick);
			$('.RemoveStudentBtn').live("click",this.RemoveStudentClick);
			
			//share
			$.liveReady('.teacherFunc', function() { if(IsTeacher) {$(this[0]).show();}else {$(this[0]).hide();}});
			$.liveReady('.nonteacherFunc', function () {if(IsTeacher) {$(this[0]).hide();}else {$(this[0]).show();}});

		  }
	
	,init: function(){
				//清空course
				$("#courseContainer").html("");
				$("#courseContainer").show();
				$("#addcourse").hide();
				$("#addclass").hide();
				$("#class").hide();
				
				//取得ME
				//Request("api/TestMe","GET",null,function(info){ alert("me:"+info.me); });
				
				//取得UserInfo
				Request("api/GetAccountInfo","GET",null,function(info){
						$("#UserNameSpan").html(info.name);
						if(info.role == "teacher"){IsTeacher = true;}
						if(IsTeacher){ 	//開啟teacher功能
							$(".teacherFunc").show();
							$(".nonteacherFunc").hide();
						}else{
							$(".teacherFunc").show();
							$(".nonteacherFunc").hide();
						}
						TA.ShowCourse();
						TA.event();
					}) 
					
				
			}

		
	//////// main menu //////////
	,HomepageBtnClick:function(){
						//$("#course").addClass("animated fadeInDown");
							$("#course").hide();
							$("#banner").show();
					}
	
	,CourseBtnClick: function(){
						TA.ShowCourse();
						$("#course").show();
							$("#courseContainer").show();
							$("#addcourse").hide();
							$("#addclass").hide();
							$("#class").hide();
						$("#banner").hide();
					}	
	
	,LogoutClick: function(){
		Request("api/Logout","GET",null, function(r){
			if(r.result == "0") alert("您已登出囉!");
			//跳轉到登入頁面
			//********************
		});
	}
	
	
	//////// course menu //////////
	,ShowCourse: function(){
					$("#courseContainer").html("");//清空course
					Request("api/GetCourse","GET",null,function(info){
						MyCourses = info;
						for(var num in MyCourses){
							var node = "<div class='box course' datanum="+num+"><h2>"+MyCourses[num].name+"</h2></div>";
							$(node).appendTo("#courseContainer");
						}
						//老師增加課程
						var node = "<div class='box addCourseBtn teacherFunc'><h2>+新增課程</h2></div>";
							$(node).appendTo("#courseContainer");
					
					})
				}
	
	,CourseOnClick: function(e){ 
						if(!$(e.target).attr("datanum"))return;
						 //$(target).index();
						 var num = $(e.target).attr("datanum");
						 NowCourseIndex = num ;
						 MyClasses = MyCourses[num].classes;
						 NowCourse = MyCourses[num];
						 
						 //hide all others
						  $('#courseContainer .box:not([datanum = "'+num+'"]),#courseContainer .box:not(.course)').remove();
						 
						 //add all class
						for(var num in MyClasses){
							var node = "<div class='box class' datanum="+num+"><h2>"+MyClasses[num].name+"</h2></div>";
							$(node).appendTo("#courseContainer");
						}
						 
						 //老師增加課堂
						var node = "<div class='box addClassBtn teacherFunc' ><h2>+新增課堂</h2></div>";
							$(node).appendTo("#courseContainer");
					
					}
	
	,ClassOnClick: function(e){ 
					if(!$(e.target).attr("datanum"))return;
					 var num = $(e.target).attr("datanum");
					 NowClass = MyClasses[num];
					 NowClassIndex = num ;
					 //hide all others course and classes
					 $("#courseContainer  .box").remove();
					 
					 //go to class
					  $("#courseContainer").hide();
						$("#addcourse").hide();
						$("#addclass").hide();
					  $("#class").show();
					  TA.ClassInit();
				}
	
	,AddCourseArea: function(){   //CLICK 增加課程
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
											TA.RefershCourse();	
									}
									if(info.error){alert("ERROR:"+info.error);}
									
								});
								
								$(currentFrom).each(function(){ //清空資料
									this.reset();
								});
								$("#addcourse").hide(); //隱藏表單
								
								return false;
							})
					}
	
	,AddClassArea: function(){
					$("#addclass").show();
					
					//去除重複周次
					$("#addclass select[name='week'] option").each( function(index, Element){
						var i = $(Element).val();
						for(var l in MyClasses){
							if(i == MyClasses[l].week){
								$(Element).remove();
							}
						}
					})
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
								TA.RefershCourse();
								$('#courseContainer .box.course[datanum="'+NowCourseIndex+'"]').click();
							});
							
							$(currentFrom).each(function(){ //清空資料
								this.reset();
							});
							$("#addclass").hide(); //隱藏表單
							
							return false;
						})
					}
	
	
	
	//////// class menu //////////
	,ClassInit :	function(){
	
				//抓active 
				var IsActiveStatus = (NowClass.active == "0")?false:true;
				if(IsActiveStatus){
					$(".classActiveLink").html("Deactive Class");
					$(".classActiveLink").val("1");
				}else{
					$(".classActiveLink").html("Active Class");
					$(".classActiveLink").val("0");
				}
				
				//抓message資料
				Request("api/GetMessageByClass","GET",{clid: NowClass.clid},function(msgs){
					
					for(var num in msgs){
						var node = "<p class='msg' datanum="+num+">"+msgs[num].account+" : "+msgs[num].content+"</p>";
						$(node).appendTo("#messagebox");
					}
				});
				//抓Quiz資料
					Request("api/GetQuizByClass","GET",{clid: NowClass.clid},function(r){
						$('#quizContainer').html("");
						
						MyQuizzes = r;
						for(var i in MyQuizzes){
							var dataToTemplate = TA.quizModelToTemplate(MyQuizzes[i],i);
							$( "#quiz-template" ).tmpl( dataToTemplate ).appendTo( "#quizContainer");
						}
					});
				
				//開啟quiz. message 的websocket
				//**********************
				
				if(IsTeacher){
					
					//抓學生資料
					Request("api/GetAllStudentInfoByCourse", "GET",{coid:NowCourse.coid},function(r){
						if(r.result == "0" ){
							MyStudents = r.list;
							var c =$('.studentContainer').find("tr:not(.title):not(.BigTitle)").remove();
							$( "#student-template" ).tmpl( MyStudents ).appendTo( ".studentContainer");
						}
					});
					
					Request("api/GetAllStudent", "GET",null,function(r){

						if(r){
							for(var i in r){
								r[i].value = r[i].account +" "+ r[i].name;
							}
							$( "#AddStudentText" ).autocomplete({
								minLength: 0,
								source: r,
								focus: function( event, ui ) {
									$( "#AddStudentText" ).val( ui.item.account );
									return false;
								},
								select: function( event, ui ) {
									$( "#AddStudentText" ).val( ui.item.account );
									$( "#AddStudentName" ).html( ui.item.name );
									return false;
								}
							}).data( "autocomplete" )._renderItem = function( ul, item ) {
								return $( "<li>" )
									.data( "item.autocomplete", item )
									.append( "<a>" + item.account + " (" + item.name + ")</a>" )
									.appendTo( ul );
							};
						}
					});
					
				
				
				}
				
			}			
	
	,ClassFuncHandler:function(target){
						$("#class .content .classFunc").hide();
						
						var testTarget = $(target);
						var divToShow = $(target.currentTarget).attr("linkDiv");
						$(divToShow).show();
					}
									
	,ClassActiveHandler:function(e){
		var IsActiveStatus = ($(e.target).val() == "0")?false:true;
			if(IsActiveStatus){
				if( confirm("是否確認關閉課程")){
					$(".classActiveLink").html("Active Class");
					$(".classActiveLink").val("0");
					NowClass.active = "0";
					
					Request("api/DeActiveClass","GET",{clid:NowClass.clid},function(info){
						//完成active
					});
				}
			}else{  
			
				var currentActiveClass ;
				for (var i in MyClasses) {
					if(i == NowClassIndex)continue;
					if(MyClasses[i].active == "1") currentActiveClass = MyClasses[i];
				}
				if((currentActiveClass == undefined) || confirm('您現在有一堂課「'+currentActiveClass.name+'」正啟動,啟動新課堂會自動關閉舊課堂')){
					$(".classActiveLink").html("Deactive Class");
					$(".classActiveLink").val("1");
					NowClass.active = "1";

					Request("api/ActiveClass","GET",{clid:NowClass.clid},function(info){
						//完成active
					});
				}
				
			}
	}
	
	,SubmitQuiz: function(e){
		var form = e.target;
		var quizDIV = $(form).parent();
		var choices = {
			A:form.A.value,
			B:form.B.value,
			C:form.C.value,
			D:form.D.value
		}
		$(form.answer).each(function(i){
			if(form.answer[i].checked) answer = form.answer[i].value;
		});
		var data =	{
			question: form.question.value,
			choices:JSON.stringify(choices),
			correctanswer:answer,
			clid:NowClass.clid,
		}
		
		if($(form).parent().attr("qid")){
			data.qid = $(form).parent().attr("qid");
		}
		
		
		Request("api/AddQuizToClass","GET",data,function(r){
			if(r.result == "0"){
				//把它從form變成div
				
				var dataToTemplate ;
				
				if(r.quiz)  { //如果是新增的資料
					MyQuizzes.push(r.quiz);	
					var index = MyQuizzes.length - 1;
					dataToTemplate = TA.quizModelToTemplate(MyQuizzes[index],index);
					
				}else{  //如果只是更新
					var index ;
					//update
					for(var i in MyQuizzes){
						if(MyQuizzes[i].qid == data.qid){
							index = i;
							var quiz = MyQuizzes[i];
							quiz.choice        = data.choices;
							quiz.correctAnswer = data.correctanswer;
							quiz.question      = data.question;
						}
					}
					
					dataToTemplate = TA.quizModelToTemplate(MyQuizzes[index],index);
				}
				
				$( "#quiz-template" ).tmpl( dataToTemplate ).insertAfter(quizDIV);
				$(quizDIV).remove();
			}
		});
		
		
		
		return false;
	}
	,AddQuizForm:function(){
					$( "#quiz-add-template" ).tmpl( null ).appendTo( "#quizContainer");
				}
	,ModifyQuizForm:function(e){
			var t = e.target;
			//var form = $(t).parent().parent();
			var thisquiz = $(t).parent().parent();
			var index = thisquiz.attr("index");
			var data = TA.quizModelToTemplate(MyQuizzes[index],index);
			$( "#quiz-add-template" ).tmpl( data ).insertAfter(thisquiz);
			$(thisquiz).remove();
	}
	,RemoveQuizForm:function(e){
			var t = e.target;
			var thisquiz = $(t).parent().parent();
			Request("api/RemoveQuiz","GET",{qid:thisquiz.attr("qid") },function(r){
				if(r.result = "0"){
					$(thisquiz).remove();
				}
			});
			//*****************
			
	}
	,ActiveQuizForm:function(e){
			var t = e.target;
			var thisquiz = $(t).parent().parent();
			$(t).css("display","none");
			$(t).siblings(".DeActiveQuiz").css("display","");
			Request("api/ActiveQuiz","GET",{qid:thisquiz.attr("qid"), active:true},function(r){
				if(r.result == "0"){
					
				}else if(r.result == "1"){
					alert("啟動失敗!");
					$(t).css("display","");
					$(t).siblings(".DeActiveQuiz").css("display","none");
				}
			});
	}
	,DeActiveQuizForm:function(e){
			var t = e.target;
			var thisquiz = $(t).parent().parent();
			$(t).css("display","none");
			$(t).siblings(".ActiveQuiz").css("display","");
			Request("api/ActiveQuiz","GET",{qid:thisquiz.attr("qid"), active:false},function(r){
				if(r.result == "0"){
					
				}else if(r.result == "1"){
					alert("關閉失敗!");
					$(t).css("display","");
					$(t).siblings(".DeActiveQuiz").css("display","none");
				}
			});
	}
	,quizModelToTemplate:function(quiz,index){
			var choices = JSON.parse(quiz.choice);
			var dataToTemplate =	{
				qid: quiz.qid,
				question: quiz.question,
				A :choices.A,
				B :choices.B,
				C :choices.C,
				D :choices.D,
				//correctA:false,
				//correctB:false,
				//correctC:false,
				//correctD:false,
				openStyle:'',
				closeStyle:''
			}
			
			switch(quiz.correctAnswer){
				case 'A':dataToTemplate.correctA =true;break;
				case 'B':dataToTemplate.correctB =true;break;
				case 'C':dataToTemplate.correctC =true;break;
				case 'D':dataToTemplate.correctD =true;break;
			}
			if(quiz.active == "0"){
				dataToTemplate.closeStyle="display:none;";
			}else{
				dataToTemplate.openStyle="display:none;";
			}
			dataToTemplate.index = index;					
			dataToTemplate.qid = quiz.qid ;
			
			return dataToTemplate;
	
	}	
	
	,AddStudentClick:function(){
		var account = $("#AddStudentText").val();
		var data = {coid:NowCourse.coid, account:account};
		Request("api/AddStudentToCourse","GET",data,function(r){
			if(r.result == "0"){
				//抓學生資料
				Request("api/GetAllStudentInfoByCourse", "GET",{coid:NowCourse.coid},function(r){
					if(r.result == "0" ){
						MyStudents = r.list;
						var c =$('.studentContainer').find("tr:not(.title):not(.BigTitle)").remove();
						$( "#student-template" ).tmpl( MyStudents ).appendTo( ".studentContainer");
					}
				});
			}else if(r.result == "1"){
				alert("錯誤:此帳號已在課程!");
			}else if(r.result == "2"){
				alert("錯誤:無此帳號!");
			}
		});
	}
	,RemoveStudentClick:function(e){
		var t = e.target;
		var account = $(t).attr("account");
		if(confirm("您確定要刪除"+account+"嗎?")){
			var data = {coid:NowCourse.coid, account:account};
			Request("api/RemoveStudentFromCourse","GET",data,function(r){
				if(r.result == "0"){
					//抓學生資料
					Request("api/GetAllStudentInfoByCourse", "GET",{coid:NowCourse.coid},function(r){
						if(r.result == "0" ){
							MyStudents = r.list;
							var c =$('.studentContainer').find("tr:not(.title):not(.BigTitle)").remove();
							$( "#student-template" ).tmpl( MyStudents ).appendTo( ".studentContainer");
						}
					});
				}else if(r.result == "1"){
					alert("錯誤:此帳號已在課程!");
				}else if(r.result == "2"){
					alert("錯誤:無此帳號!");
				}
			});
		}
	}
	
	
	//----------------------------share function---------------------
	,RefershCourse: function(){
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
}


 
TA.init();


})