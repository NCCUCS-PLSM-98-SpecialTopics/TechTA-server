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
						if(result.code =="100"){
							alert("請重新登入!"); 
							window.location = "Login";
							return;
						}
					}
					callback(result);
			},
			error: function(e){
					
					timer = null;
					alert("ERROR : "+e.responseText)
			},
		});	
}
var Me;

var MyCourses;
var NowCourseIndex;
var NowCourse;

var MyClasses;
var NowClassIndex;
var NowClass;

var MyQuizzes;
var MyStudents;

var AllStudent;


var IsTeacher = false; // not null is teacher  class="teacherFunc"

///////////////////////////////Event Control///////////////////////////////////////////
var TA = {
	event: function(){	
			//main menu
			$("#courseBtn").live("click",this.CourseBtnClick);
			$("#homepageBtn").live("click",this.HomepageBtnClick);
			$("#profileBtn").live("click",this.ProfileBtnClick);
			$("#LogoutBtn").live("click",this.LogoutClick);
			
			//course
			$("#courseContainer .addCourseBtn").live("click",this.AddCourseArea);
			$("#courseContainer .addClassBtn").live("click",this.AddClassArea);
			$('#courseContainer .box.course').live("click",this.CourseOnClick);
			$('#courseContainer .box.class').live("click",this.ClassOnClick);
			$("#addcourse form").live("submit",this.CourseOnSubmit);
			$("#addclass form").live("submit",this.ClassOnSubmit);
			
			
			//class
			$('.classfuncLink').live("click",this.ClassFuncHandler);
			$('.classActiveLink').live("click",this.ClassActiveHandler);
				//quiz
				$('#quizContainer form').live("submit",this.SubmitQuiz);
				$('#AddQuestionBtn').live("click",this.AddQuizForm);
				$('#quizContainer .ModifyQuiz').live("click",this.ModifyQuizForm);
				$('#quizContainer .RemoveQuiz').live("click",this.RemoveQuizForm);
				$('#quizContainer .ActiveQuiz').live("click",this.ActiveQuizForm);
				$('#quizContainer .DeActiveQuiz').live("click",this.DeActiveQuizForm);
				//student
				$('#AddStudentBtn').live("click",this.AddStudentClick);
				$('.RemoveStudentBtn').live("click",this.RemoveStudentClick);
				//message
				$('.SendMessageBtn').live("click",this.SendMessage);
				$('.BonusPart').live("click",this.BonusMessage);
				//Quiz
				$('.answerQuiz .answer.box').live("click",this.TakeQuiz);
				//Start
				$('.control .pptStart').live("click",this.PPTStart);
				$('#PPTClose').live("click",this.PPTClose);
				$('#PanelOnOff').live("click",this.PanelOnOff);
				$('#PAddBonusStudentBtn').live("click",this.AddBonusStudent);
				$('.PDetailBtn').live("click",this.ShowPDetail);
				$('.PActiveBtn').live("click",this.ActivePQuizForm);
				$('.PDeActiveBtn').live("click",this.DeActivePQuizForm);
				
				
			//share
			$.liveReady('.teacherFunc', function() { if(IsTeacher) {$(this[0]).show();}else {$(this[0]).hide();}});
			$.liveReady('.nonteacherFunc', function () {if(IsTeacher) {$(this[0]).hide();}else {$(this[0]).show();}});
			$('.testBtn').live("click",this.test);
			//template
			$('.get-started').live("click",this.CourseBtnClick);
			$('.watch-demo').live("click",this.CourseBtnClick);
			
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
						Me = info;
						$("#UserNameSpan").html(info.name);
						if(info.role == "teacher"){IsTeacher = true;}
						if(IsTeacher){ 	//開啟teacher功能
							$(".teacherFunc").show();
							$(".nonteacherFunc").hide();
						}else{
							$(".teacherFunc").show();
							$(".nonteacherFunc").hide();
						}
						WSClient.init(Me,TA.OnMessage);
						TA.ShowCourse();
						TA.event();
					}); 
				
				Request("api/GetAllStudent", "GET",null,function(r){
					AllStudent = r;
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

		
	//////// main menu //////////
	,HomepageBtnClick:function(){
						//$("#course").addClass("animated fadeInDown");
							$("#course").hide();
							$("#banner").show();
							$("#profile").hide();
							
					}
	,CourseBtnClick: function(){
						TA.ShowCourse();
						$("#course").show();
							$("#courseContainer").show();
							$("#addcourse").hide();
							$("#addclass").hide();
							$("#class").hide();
						$("#banner").hide();
						$("#profile").hide();
						return false;
					}	
	,ProfileBtnClick:function(){
						$("#course").hide();
						$("#banner").hide();
						$("#profile").show();
						TA.ShowAccountInfo();
					}		
	,LogoutClick: function(){
		Request("api/Logout","GET",null, function(r){
			if(r.result == "0") alert("您已登出囉!");
			//跳轉到登入頁面
			window.location = "Login";
		});
	}
	
	////////Profile ////////////////
	,ShowAccountInfo: function(){
				Request("api/GetAccountInfo","GET",null,function(info){
					$("#UserNameSpan").html(info.name);
					$("#U_account").val();
					$("#U_name").val();
					$("#U_email").val();
					$("#U_department").val();
				}) 
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
						if(IsTeacher){
							var node = "<div class='box addCourseBtn teacherFunc'><h2>+新增課程</h2></div>";
								$(node).appendTo("#courseContainer");
						}
					
					})
				}
	,CourseOnClick: function(e){ 
						if(!$(e.currentTarget).attr("datanum"))return;
						 //$(target).index();
						 var num = $(e.currentTarget).attr("datanum");
						 NowCourseIndex = num ;
						 MyClasses = MyCourses[num].classes;
						 NowCourse = MyCourses[num];
						 
						 //hide all others
						  $('#courseContainer .box:not([datanum = "'+num+'"]),#courseContainer .box:not(.course)').remove();
						 
						 //add all class
						for(var num in MyClasses){
							var node = "<div class='box class' datanum="+num+"><h2>"+(parseInt(num,10)+1)+". "+MyClasses[num].name+"</h2></div>";
							$(node).appendTo("#courseContainer");
						}
						 
						 //老師增加課堂
						if(IsTeacher){
							var node = "<div class='box addClassBtn teacherFunc' ><h2>+新增課堂</h2></div>";
								$(node).appendTo("#courseContainer");
						}
					}
	,ClassOnClick: function(e){ 
					if(!$(e.currentTarget).attr("datanum"))return;
					 var num = $(e.currentTarget).attr("datanum");
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
					
					}
	,CourseOnSubmit: function(target){
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
							}
	,ClassOnSubmit: function(target){
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
					TA.ClearMessageBox();
					for(var num in msgs){
						TA.AddMessageToBox(msgs[num]);
					}
				});
				//隱藏TakeQuiz
					$('.noQuizMessage').show();
					$('#ansQuiz').hide();
				
				//抓Quiz資料
					Request("api/GetQuizByClass","GET",{clid: NowClass.clid},function(r){
						$('#quizContainer').html("");
						
						MyQuizzes = r;
						var activeQuiz = undefined;
						for(var i in MyQuizzes){
							var dataToTemplate = TA.quizModelToTemplate(MyQuizzes[i],i);
							$( "#quiz-template" ).tmpl( dataToTemplate ).appendTo( "#quizContainer");
							if(MyQuizzes[i].active == "1") activeQuiz = MyQuizzes[i];
						}
						$( "#quizContainer").children().each(function(index, e) {
							var qid = $(e).attr("qid");
							var BarChartElement = $(e).children(".BarChart").get(0);
							var index = Tool.indexOfKey(MyQuizzes,"qid",qid);
							if(index!=-1){
								TA.MakeQuizBarChart(BarChartElement, MyQuizzes[index]);
							}
						});

						TA.SetTakeQuiz(activeQuiz);
						
					});
				
				
				if(IsTeacher){
					
					//抓學生資料
					Request("api/GetAllStudentInfoByCourse", "GET",{coid:NowCourse.coid},function(r){
						if(r.result == "0" ){
							MyStudents = r.list;
							var c =$('.studentContainer').find("tr:not(.title):not(.BigTitle)").remove();
							$( "#student-template" ).tmpl( MyStudents ).appendTo( ".studentContainer");
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
		var IsActiveStatus = ($(e.currentTarget).val() == "0")?false:true;
			if(IsActiveStatus){
				if( confirm("是否確認關閉課程")){
					$(".classActiveLink").html("Active Class");
					$(".classActiveLink").val("0");
					NowClass.active = "0";
					for(var i in MyQuizzes){
						if(MyQuizzes[i].active == "1"){
							$('#quizContainer .quiz[qid='+MyQuizzes[i].qid+']').find(".DeActiveQuiz").click();
						}
					}
					
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
	
	//Quiz
	,SubmitQuiz: function(e){
		var form = e.currentTarget;
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
				var index;
				if(!data.qid)  { //如果是新增的資料
					MyQuizzes.push(r.quiz);	
					index = MyQuizzes.length - 1;
					//dataToTemplate = TA.quizModelToTemplate(MyQuizzes[index],index);
				}else{  //如果只是更新
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
					
				}
				
				dataToTemplate = TA.quizModelToTemplate(MyQuizzes[index],index);
				$( "#quiz-template" ).tmpl( dataToTemplate ).insertAfter(quizDIV);
				$(quizDIV).remove();
				$( "#quizContainer").children('.quiz[qid='+MyQuizzes[index].qid+']').each(function(index, e) {
					var BarChartElement = $(e).children(".BarChart").get(0);
					TA.MakeQuizBarChart(BarChartElement, MyQuizzes[index]);
					
				});

			}
		});
		
		
		
		return false;
	}
	,AddQuizForm:function(){
					$( "#quiz-add-template" ).tmpl( null ).appendTo( "#quizContainer");
				}
	,ModifyQuizForm:function(e){
			var t = e.currentTarget;
			//var form = $(t).parent().parent();
			var thisquiz = $(t).parent().parent();
			var index = thisquiz.attr("index");
			var data = TA.quizModelToTemplate(MyQuizzes[index],index);
			$( "#quiz-add-template" ).tmpl( data ).insertAfter(thisquiz);
			$(thisquiz).remove();
			MyQuizzes[index].chart = undefined;
	}
	,RemoveQuizForm:function(e){
			var t = e.currentTarget;
			var thisquiz = $(t).parent().parent();
			Request("api/RemoveQuiz","GET",{qid:thisquiz.attr("qid") },function(r){
				if(r.result = "0"){
					$(thisquiz).remove();
				}
			});
			//*****************
			
	}
	,ActiveQuizForm:function(e){
			var t = e.currentTarget;
			var thisquiz = $(t).parent().parent();
			if(NowClass.active == "0"){
				alert("課程未啟動時，問題無法啟動");
				return;
			}
			for(var i in MyQuizzes){
				if(MyQuizzes[i].active == "1" && 
				   MyQuizzes[i].qid != thisquiz.attr("qid")){
				   if( !confirm("您有一問題正啟動當中，是否為您關閉？\n（"+MyQuizzes[i].question+"）"))
					{
						 return;
					}else{
						thisquiz.siblings(".quiz[qid="+MyQuizzes[i].qid+"]").find(".DeActiveQuiz").click();
					}
				}
			}
			for(var i in MyQuizzes){
				if(MyQuizzes[i].qid == thisquiz.attr("qid")){
					 MyQuizzes[i].active = "1";
				}
			}
			
			
			$(t).css("display","none");
			$(t).siblings(".DeActiveQuiz").css("display","");
			$(t).siblings(".RemoveQuiz").css("display","none");
			$(t).siblings(".ModifyQuiz").css("display","none");
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
			var t = e.currentTarget;
			var thisquiz = $(t).parent().parent();
			for(var i in MyQuizzes){
				if(MyQuizzes[i].qid == thisquiz.attr("qid")){
					 MyQuizzes[i].active = "0";
				}
			}
			$(t).css("display","none");
			$(t).siblings(".ActiveQuiz").css("display","");
			$(t).siblings(".RemoveQuiz").css("display","");
			$(t).siblings(".ModifyQuiz").css("display","");
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
	,UpdateQuizStatistic: function(data){
		//考試統計
		for(var i in MyQuizzes){
			if(MyQuizzes[i].qid == data.qid) {
				MyQuizzes[i].student[data.account]  = data.content;
			}
		}
		//Refersh QuizStatic
		//*********************
		$( "#quizContainer").children('.quiz[qid='+data.qid+']').each(function(index, e) {
			var qid = $(e).attr("qid");
			var BarChartElement = $(e).children(".BarChart").get(0);
			var index = Tool.indexOfKey(MyQuizzes,"qid",qid);
			if(index!=-1){
				TA.MakeQuizBarChart(BarChartElement, MyQuizzes[index]);
			}
		});
		$( "#PQuiz").children('.APQuiz[qid='+data.qid+']').each(function(index, e) {
			var qid = $(e).attr("qid");
			var BarChartElement = $(e).find(".BarChart").get(0);
			var index = Tool.indexOfKey(MyQuizzes,"qid",qid);
			if(index!=-1){
				TA.MakeQuizBarChart(BarChartElement, MyQuizzes[index]);
			}
		});


	}
	//Quizchart
	,MakeQuizBarChart:function(element,quiz){
		var AnswerStatisticData=[];//A B C D
		var AnsCount = [0,0,0,0];
		var NameCount= ["","","",""];
		for(var account in quiz.student){
			var index = Tool.indexOfKey(AllStudent,"account",account);
			var stdName = AllStudent[index].name;
			if(index != -1){
				var stdAns = quiz.student[account]; 
				switch(stdAns){
					case 'A' : AnsCount[0]++; NameCount[0] += stdName+"<br/>" ;break;
					case 'B' : AnsCount[1]++; NameCount[1] += stdName+"<br/>" ;break;
					case 'C' : AnsCount[2]++; NameCount[2] += stdName+"<br/>" ; break;
					case 'D' : AnsCount[3]++; NameCount[3] += stdName+"<br/>" ;break;
					default:console.log("[ERROR][TA/MakeQuizBarChart] for stdAns:"+stdAns); break;
				}
				
				AnswerStatisticData = [{
					name: NameCount[0],
					color: '#4572A7',
					y: AnsCount[0]
				}, {
					name: NameCount[1],
					color: '#AA4643',
					y: AnsCount[1]
				}, {
					name: NameCount[2],
					color: '#89A54E',
					y: AnsCount[2]
				}, {
					name: NameCount[3],
					color: '#80699B',
					y: AnsCount[3]
				}]
				var ttt = JSON.stringify(AnswerStatisticData);
				
			}
		}
		
		if(!quiz.chart){
			quiz.chart = new Highcharts.Chart({
				chart: {
					renderTo: element,
					type: 'column'
				},
				xAxis: {
					categories: ['A', 'B', 'C', 'D']
				},
				yAxis: {
					min: 0,
					title: {
						text: 'Total Number'
					},
					stackLabels: {
						enabled: true,
						style: {
							fontWeight: 'bold',
							color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
						}
					}
				},
				title: {
					text: ''
				},
				legend: {
					enabled:false
				},
				tooltip: {
					formatter: function() {
						return "<p>"+this.point.name+"</p>";
					}
				},
				plotOptions: {
					column: {
						stacking: 'normal',
						dataLabels: {
							enabled: true,
							color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
						}
					}
				},
				series: [{
					data: AnswerStatisticData
				}]
			});
		}else{
			quiz.chart.series[0].setData(AnswerStatisticData);
		}
	
	}
	
	//Student
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
		var t = e.currentTarget;
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
	
	//message
	,SendMessage: function(e){
		if(NowClass.active == "0"){
			alert("課程未啟動時，無法發言");
			return;
		}
		var text = $('.SendMessageText').val();
		if(text && text!=""){
			WSClient.sendMsg(text,NowClass);
			//----
			TA.AddMessageToBox({account:"Me",content:text})
		}
	}
	,BonusMessage: function(e){
		var t = e.currentTarget;
		var msgDiv = $(t).parent();
		var mid = msgDiv.attr("mid");
		var bonus = msgDiv.attr("bonus");
		if(bonus == "0"){ //加分
			$(".msg[mid="+mid+"]").attr("bonus","1");
			$(".msg[mid="+mid+"]").find('.coin').css('z-index','21');
			$(".msg[mid="+mid+"]").find('.coin').addClass("goldCoinRotateNotDisappear");
			setTimeout(function(){$(".msg[mid="+mid+"]").find('.coin').removeClass('goldCoinRotateNotDisappear');},2000);
			Request("api/MessageBonus","GET",{mid:mid},function(r){ 
				if(r.result!="0"){
					//server error
				}
			});
		}else{ //扣分
			$(".msg[mid="+mid+"]").attr("bonus","0");
			$(".msg[mid="+mid+"]").find('.coin').css('z-index','0');
			$(".msg[mid="+mid+"]").find('.coin').removeClass("goldCoinRotateNotDisappear");
			Request("api/MessageBonus","GET",{mid:mid,minus:true},function(r){ 
				if(r.result!="0"){
					//server error
				}
			});
		}
	
	}
	,OnMessage: function(e){
		var wsMessage = e.data;
		console.log("[TA/onmessage] " + wsMessage);
		var wsMessageObj = JSON.parse(wsMessage);
		var data = JSON.parse(wsMessageObj.data);
		if(!data||!data.type||!data.clid){
			console.log("[ERROR][TA/onmessage] no data or no data.type of clid");
			return;
		}
		if(data.clid != NowClass.clid)return;
		switch (data.type){
			case "message"://message加東西
				data.mid = wsMessageObj.msg_uuid;
				TA.AddMessageToBox(data);
				break; 
			case "answer": 
				if(IsTeacher){
					TA.UpdateQuizStatistic(data);
				}
				break; //老師:統計問題
			case "bonus": //做加分的特效
				if(data.student = Me.account){
					TA.BonusEffect();
				}break; 
			case "class":
				for(var i in MyCourses){
					for(var j in MyCourses[i].classes){
						if(MyCourses[i].classes[j].clid = data.clid){
							MyCourses[i].classes[j].active = "1";
						}
					}
				}
				break; //做啟動課程的特效
			case "closeclass": 
				for(var i in MyCourses){
					for(var j in MyCourses[i].classes){
						if(MyCourses[i].classes[j].clid = data.clid){
							MyCourses[i].classes[j].active = "0";
						}
					}
				}
				break; //關閉啟動課程的特效
			case "quiz": 
				if(!IsTeacher){
					TA.SetTakeQuiz(data)
				}
				break; //做考卷的特效
			case "quizclose": 
				if(!IsTeacher){
					TA.SetTakeQuiz();
				}
				break; //關閉考卷的特效
			default : console.log("[ERROR][TA/onmessage] error type");break;
			
		}
		
	
	}
	
	//Take Quiz
	,SetTakeQuiz: function(Quiz){
		if(!Quiz){
			$('.noQuizMessage').show();
			$('#ansQuiz').hide();
		}else{
			$('.noQuizMessage').hide();
			$('#ansQuiz').show();
			$('#ansQuiz .question').html(Quiz.question);
			
			$('#ansQuiz').attr("qid",Quiz.qid);
			
			var choice = JSON.parse(Quiz.choice);
			$('#ansQuiz .answer.A').html(choice.A);
			$('#ansQuiz .answer.B').html(choice.B);
			$('#ansQuiz .answer.C').html(choice.C);
			$('#ansQuiz .answer.D').html(choice.D);
		}
	
	}
	,TakeQuiz: function(e){
		var t =  e.currentTarget;
		var answer="";
		if ($(t).hasClass("A")){answer = "A"};
		if ($(t).hasClass("B")){answer = "B"};
		if ($(t).hasClass("C")){answer = "C"};
		if ($(t).hasClass("D")){answer = "D"};
		var quiz = $(t).parent().parent();
		var qid = quiz.attr("qid");
		var cls = NowClass;
		
		WSClient.sendAns(qid, answer, cls);
		TA.SetTakeQuiz();
	}
	
	//PPTStart
	,PPTStart: function(){
		
		var url  = $('input.pptUrl').val();
		if(!url|| url ==""){alert("您未輸入連結");return;}
		
		$('#status').show();
		$('#powerpoint').show();
		$('body').css('overflow-y', 'hidden');
		$('#powerpointFreme').attr("src", "https://view.officeapps.live.com/op/embed.aspx?src="+url);
		TA.InitPanel();
	}
	,PPTClose: function(){
		$('#status').hide();
		$('#powerpoint').hide();
		if(NowClass!=null)TA.ClassInit();
	}
	,PanelOnOff: function(){
		var OpenDisplay = $('#PanelOpen').css('display');
		if(OpenDisplay == "none"){ //(目前開啟)>> 執行關閉
			$('#PanelClose').hide();
			$('#PanelOpen').show();
			$('#Pworkspace').hide();
		}else{//(目前關閉)>> 執行開啟
			$('#PanelClose').show();
			$('#PanelOpen').hide();
			$('#Pworkspace').show();
		}
	}

	,InitPanel:function(){
		//quiz
			$("#PQuiz").html("");
			for(var i in MyQuizzes){
				//問題列表
				var templateQuiz = TA.quizModelToTemplate(MyQuizzes[i],i);
				$( "#PPT-Quiz-template" ).tmpl( templateQuiz ).appendTo( "#PQuiz");
				//清除所有chart
				MyQuizzes[i].chart = undefined;
			}
		//message
			
			//複製class的mag過來OK
			//WS接收過來OK
			//要可以加分OK
		//bonus
			//自動完成PBonus
			
		for(var i in MyStudents){
			MyStudents[i].value = MyStudents[i].account +" "+ MyStudents[i].name;
		}
		$( "#PAddBonusStudentText" ).autocomplete({
			minLength: 0,
			source: MyStudents,
			open: function(){
					$(this).autocomplete('widget').css('z-index', 100);
					return false;
				},
			focus: function( event, ui ) {
				$( "#PAddBonusStudentText" ).val( ui.item.account );
				return false;
			},
			select: function( event, ui ) {
				$( "#PAddBonusStudentText" ).val( ui.item.account );
				$( "#PAddBonusStudentName" ).html( ui.item.name );
				return false;
			}
		}).data( "autocomplete" )._renderItem = function( ul, item ) {
			return $( "<li>" )
				.data( "item.autocomplete", item )
				.append( "<a>" + item.account + " (" + item.name + ")</a>" )
				.appendTo( ul );
		};
	}
	
	,ActivePQuizForm:function(e){
			var t = e.currentTarget;
			var thisquiz = $(t).parent();
			//課程未啟動時，問題無法啟動
			if(NowClass.active == "0"){
				alert("課程未啟動時，問題無法啟動");
				return;
			}
			//您有一問題正啟動當中，是否為您關閉？
			for(var i in MyQuizzes){
				if(MyQuizzes[i].active == "1" && 
				   MyQuizzes[i].qid != thisquiz.attr("qid")){
				   if( !confirm("您有一問題正啟動當中，是否為您關閉？\n（"+MyQuizzes[i].question+"）"))
					{
						return;
					}else{
						thisquiz.siblings(".quiz[qid="+MyQuizzes[i].qid+"]").find(".PDeActiveBtn").click();
					}
				}
			}
			
			Request("api/ActiveQuiz","GET",{qid:thisquiz.attr("qid"), active:true},function(r){
				if(r.result == "0"){
					var  i = Tool.indexOfKey(MyQuizzes, "qid", thisquiz.attr("qid"))
					if(i!=-1)MyQuizzes[i].active = "1";
					$(t).css("display","none");
					$(t).siblings(".PDeActiveBtn").css("display","");
				}else if(r.result == "1"){
					alert("啟動失敗!");
					$(t).css("display","");
					$(t).siblings(".PDeActiveBtn").css("display","none");
				}
			});
	}
	,DeActivePQuizForm:function(e){
			var t = e.currentTarget;
			var thisquiz = $(t).parent();
			
			
			Request("api/ActiveQuiz","GET",{qid:thisquiz.attr("qid"), active:false},function(r){
				if(r.result == "0"){
					var  i = Tool.indexOfKey(MyQuizzes, "qid", thisquiz.attr("qid"))
					if(i!=-1)MyQuizzes[i].active = "1";
					$(t).css("display","none");
					$(t).siblings(".PActiveBtn").css("display","");
				}else if(r.result == "1"){
					alert("關閉失敗!");
					$(t).css("display","");
					$(t).siblings(".DeActiveQuiz").css("display","none");
				}
			});
	}
	,ShowPDetail:function(e){
			var t = e.currentTarget;
			var thisquiz = $(t).parent();
			var on = $(t).attr("on");
			if(on=="0"){ //開啟詳細
				thisquiz.find(".APQuizDetail").css("display","");
				var BarChartElement = thisquiz.find('.BarChart').get(0);
				var index = Tool.indexOfKey(MyQuizzes,"qid",thisquiz.attr("qid"));
				if(index!=-1){
					TA.MakeQuizBarChart(BarChartElement, MyQuizzes[index]);
				}
				$(t).attr("on","1");
			}else{//關閉詳細
				thisquiz.find(".APQuizDetail").css("display","none");
				$(t).attr("on","0");
			}
	
	
	
	}
	,AddBonusStudent:function(e){
		var account = $('#PAddBonusStudentText').val();
		var data = {
			account :account,
			coid: NowCourse.coid
		}
		Request("api/Bonus","GET",data,function(r){
			if(r.result == "0"){
				alert("加分!")
			}
			else if(r.result == "2"){
				alert("錯誤:無此帳號!");
			}
		});
	}
	
	
	,AddMessageToBox:function(msg){
			var node;
			var data = {
				mid : msg.mid
				,bonus :(msg.bonus)?(msg.bonus):0
				,user :msg.account
				,content :msg.content
			}
			if(IsTeacher){data.IsTeacher = true;}
			$( "#message-template" ).tmpl( data).appendTo( ".messagebox");
			$(node).appendTo(".messagebox");
	}
	,ClearMessageBox:function(){
			$(".messagebox").html("");
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
	
	,test: function(){
		$('#status').show();
		$('#powerpoint').show();	
		$('#powerpointFreme').attr("src", "https://view.officeapps.live.com/op/embed.aspx?src=http://jackliit.dyndns.tv/TechTA/cs.ppt");

	}
	
	,BonusEffect: function(){
		$('#status').show();
		$('#cash').show();
		$('#cash').addClass('goldCoinRotate');
		
		setTimeout(function(){
			$('#status').hide();
			$('#cash').hide();
			$('#cash').removeClass('goldCoinRotate');
		
		},2000);
	}
}


var Tool = {
	indexOfKey:function(arr, key, value){
		for(var i in arr){
			if(arr[i][key] == value){
				return i;
			}
		
		}
		return -1;
	}


}




TA.init();
 



})