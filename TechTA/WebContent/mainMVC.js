// JavaScript Document
jQuery(document).ready(function() {


//////////////////////////////////Share Global/////////////////////////////////////////////
var Request = function(path,method,data,callback){
	$.ajax({
		url: path,
		data: data,
		type: method,
		dataType: "json",
		success: function(result){
				if(result&&result.error){
					if(result.code =="100"){alert("請重新登入!"); return;}
				}
				callback(result);
		},
		error: function(e){
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

var NowQuiz;

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
						  $('#courseContainer .box:not([datanum = "0"]),#courseContainer .box:not(.course)').remove();
						 
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
						var node = "<p class='box msg' datanum="+num+">"+msgs[num].account+" : "+msgs[num].content+"</p>";
						$(node).appendTo("#messagebox");
					}
				});
				
				//抓Quiz資料
				Request("api/GetQuizByClass","GET",{clid: NowClass.clid},function(r){
					$('#quizContainer').html("");
					
					NowQuiz = r;
					for(var num in r){
						var quiz = r[num];
						var data =	{
							qid: quiz.qid,
							question: quiz.question
						}
						
						var choices = JSON.parse(quiz.choice)
						data.A = choices.A;
						data.B = choices.B;
						data.C = choices.C;
						data.D = choices.D;
						
						data.correctA ="　";
						data.correctB ="　";
						data.correctC ="　";
						data.correctD ="　";
						
						switch(quiz.correctAnswer){
							case 'A':data.correctA ="Ｖ";break;
							case 'B':data.correctB ="Ｖ";break;
							case 'C':data.correctC ="Ｖ";break;
							case 'D':data.correctD ="Ｖ";break;
						}
						if(quiz.active == "0"){
							data.openStyle="";
							data.closeStyle="display:none;";
						}else{
							data.openStyle="";
							data.closeStyle="display:none;";
						}
						data.index = num;	
						data.qid = quiz.qid ;
						var a = $( "#quiz-template" ).tmpl( null );
						$( "#quiz-template" ).tmpl( data ).appendTo( "#quizContainer");
					}
				});
				//開啟quiz 的websocket
				//**********************
				
				if(IsTeacher){
					
					
					//抓學生資料
					//
				
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
		var test = $(form).children('input[name=answer]:checked');
		var test2 = $(form).children('input[name=answer]');
		var test3 = $(form).children('input');
		var test4 = $(form).children();
		var answer = $(form).children('input[name=answer]:checked').val();
		
		var data =	{
			question: form.question.value,
			choices:JSON.stringify(choices),
			correctanswer:answer,
			clid:NowClass.clid,
		}
		
		if($(form).parent().attr("qid") != undefined){
			data.qid = $(form).parent().attr("qid");
		}
		
		Request("api/AddQuizToClass","GET",data,function(r){
			//把它從form變成div
			//$(form).parent().Remove();
			$( "#quiz-template" ).tmpl( null ).insertAfter(quizDIV);
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
			
			var quiz = NowQuiz[index];
			var choices = JSON.parse(quiz.choice);
			var data =	{
				qid: quiz.qid,
				question: quiz.question,
				A :choices.A,
				B :choices.B,
				C :choices.C,
				D :choices.D,
				correctA:'　',
				correctB:'　',
				correctC:'　',
				correctD:'　',
				openStyle:'',
				closeStyle:''
			}
			
			switch(quiz.correctAnswer){
				case 'A':data.correctA ="checked";break;
				case 'B':data.correctB ="checked";break;
				case 'C':data.correctC ="checked";break;
				case 'D':data.correctD ="checked";break;
			}
			if(quiz.active == "0"){
				data.closeStyle="display:none;";
			}else{
				data.openStyle="display:none;";
			}
			data.index = index;					
			data.qid = quiz.qid ;
			$( "#quiz-add-template" ).tmpl( data ).insertAfter(thisquiz);
			$(thisquiz).remove();
	}
	,RemoveQuizForm:function(e){
			var t = e.target;
			var form = $(t).parent().parent();
			var thisquiz = $(t).parent().parent().parent();
			//Request
			//*****************
			$(thisquiz).Remove();
			
	}
	,ActiveQuizForm:function(e){
			$( "#quiz-add-template" ).tmpl( null ).appendTo( "#quizContainer");
	}
	,DeActiveQuizForm:function(e){
			$( "#quiz-add-template" ).tmpl( null ).appendTo( "#quizContainer");
	}
	
	/*
			$('#quizContainer .ModifyQuiz').live("click",this.ModifyQuizForm);
			$('#quizContainer .RemoveQuiz').live("click",this.RemoveQuizForm);
			$('#quizContainer .ActiveQuiz').live("click",this.ActiveQuizForm);
			$('#quizContainer .DeActiveQuiz').live("click",this.DeActiveQuizForm);*/
	
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