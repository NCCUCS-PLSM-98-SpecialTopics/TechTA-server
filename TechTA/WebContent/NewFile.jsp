<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>無標題文件</title>

<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<!--<script type="text/javascript" src="jquery-1.8.2.min.js" ></script>-->
<script type="text/javascript" src="jquery.tmpl.min.js"></script>
<!--<script type="text/javascript" src="jquery-ui-1.9.2.custom.min.js"></script>-->
<script type="text/javascript" src="jquery.liveready-1.0-min.js"></script>
<!--<link rel="stylesheet" type="text/css" href="hex.css" />-->


<link rel="stylesheet" type="text/css" href="animateTA.css" />
<link rel="stylesheet" type="text/css" href="animate.css" />
<link rel="stylesheet" type="text/css" href="main.css" />
<link rel="stylesheet" type="text/css" href="table.css" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
<!--<link rel="stylesheet" type="text/css" href="jquery-ui-1.9.2.custom.min.css" /> -->


</head>
<body>
<div id ="status">
	<div id="wait"></div>
	<img id="cash" src="images/cash.png" class=""/>
</div>
<div class="header">
	<div class="middleArea">
        <div class="topic">
            <h1>TechTA</h1>
        </div>
        <div class="menu">
            <a id="homepageBtn">HOME</a>
            <a id="courseBtn">MY Course</a>
            <a id="profileBtn"> MY Profile</a>
            <a id="LogoutBtn"><span id="UserNameSpan">JACK</span> 您好, 登出</a>
        </div>
    </div>
</div>

<div id="workshop">

    <div id="banner" class="banner" >
      <div class="photo">
          <div class="topic">
            <h1>TECHNOLOGY TEACHING ASSISTANT</h1>
            <h2>The Most Immediate Method To Learn </h2>
              <br/>
              <br/>
            
              <a class="get-started" href="/templates/">Get Started</a>
              <a href=".watch-video" class="watch-demo">Watch Demo</a>
            
          
          </div>
      </div>
    </div>
    <div id="course" style="display:none;" >
        <div id="courseContainer" class="middleArea clearfix" style="display:none;"  >
                    
               <div class="box"><h2>計算機概論</h2></div>
               <div class="box"><h2>計算機程式設計</h2></div>
               <div class="box"><h2>程式語言</h2></div>
               <div class="box"><h2>系統程式</h2></div>
               <div class="box"><h2>人工智慧</h2></div>
               <div class="box"><h2>計算機組織與結構</h2></div>
                <!--六角形
                <div class="hex-row">
                    <div class="hex"><div class="top"></div><div class="middle"><div class="text">計算機概論</div></div><div class="bottom"></div></div>
                    <div class="hex"><div class="top"></div><div class="middle"></div><div class="bottom"></div></div>
                    <div class="hex"><div class="top"></div><div class="middle"></div><div class="bottom"></div></div>
                </div>
                
                
                <div class="hex-row even">
                    <div class="hex"><div class="top"></div><div class="middle"></div><div class="bottom"></div></div>
                    <div class="hex"><div class="top"></div><div class="middle"></div><div class="bottom"></div></div>
                </div>
                <div class="hex-row">
                    <div class="hex"><div class="top"></div><div class="middle"></div><div class="bottom"></div></div>
                    <div class="hex"><div class="top"></div><div class="middle"></div><div class="bottom"></div></div>
                    <div class="hex"><div class="top"></div><div class="middle"></div><div class="bottom"></div></div>
                </div>-->
           
        
        </div>
        
        <div id="addcourse" class="middleArea clearfix" style="display:none;"  >
            <form onsubmit="return false;">
                課程名稱:<input type="text" name="name"/>
                年份:<select name="year">
                        <option value="98">98</option>
                        <option value="99">99</option>
                        <option value="100">100</option>
                    </select>
                學期:<select name="semester">
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                <input type="button" value="取消"/>
                <input type="submit" value="新增"/>
            </form>
        </div>
        
        <div id="addclass" class="middleArea clearfix" style="display:none;"  >
             <form onsubmit="return false;">
                課堂名稱:<input type="text" name="name"/>
                周次:<select name="week">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                    </select>
                <input type="button" value="取消"/>
                <input type="submit" value="新增"/>
            </form>
        </div>
        
        <div id="class" class="clearfix" style="display:none;"  >
                
        
            <div class="class-menu middleArea" style="">
                <div class="sidebar">
                    <span class="title" >計算機概論</span>
                    <nav>
                        <ul>
                            <li class="teacherFunc"><a class="classActiveLink" linkDiv="" value="active" >Active Class</a></li>
                            <!--<li class="teacherFunc DeAtiveClass" style="display:none;"><a class="classfuncLink DeAtiveClass" linkDiv="" >Deactive Class</a></li>-->
                            <li class="nonteacherFunc">     <a class="classfuncLink" linkDiv="#class .content .answerQuiz" >Quiz</a></li>
                            <li class="teacherFunc">  <a class="classfuncLink" linkDiv="#class .content .answerQuizTeacher" >Quiz</a></li>
                            <li >                        <a class="classfuncLink" linkDiv="#class .content .message" >Message</a></li>
                            <li class="teacherFunc" ><a class="classfuncLink" linkDiv="#class .content .control" >Control</a></li>
                            <li class="teacherFunc" ><a class="classfuncLink" linkDiv="#class .content .student" >Student</a></li>
                        </ul>
                    </nav>
                </div>
                <div class="content">
                    
                    <div class="classFunc answerQuiz" style="display: none">
                        <div id ="aQuiz">
                            <div class = "question">What is array?</div>
                            <div class = "answerContainer">
                                <div class = "answer box">A</div>
                                <div class = "answer box">B</div>
                                <div class = "answer box">C</div>
                                <div class = "answer box">D</div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="classFunc message" style="display: none">
                        
                        <!--訊息框--><div id="messagebox" class="messagebox" placeholder="目前無新訊息"></div>
                        
                        <!--發送框--><input type="text" placeholder="請輸入訊息..." />
                        			<input type="button" value="送出"/>

                    </div>
                    
                    <div class="classFunc answerQuizTeacher" style="display: none">
                        
                        <div id="quizContainer">
                            <div class ="quiz">
                                
                                    <div class = "question">WHY  A.AAAA?</div>
                                    <div class = "answerContainer" style="width:500px">
                                        <ul>
                                            <li><span class="correctAnswer"></span> <span class="Answer" > A.AAAA</span></li>
                                            <li><span class="correctAnswer"></span> <span class="Answer" > A.AAAA</span></li>
                                            <li><span class="correctAnswer"></span> <span class="Answer" > A.AAAA</span></li>
                                            <li><span class="correctAnswer"></span> <span class="Answer" > A.AAAA</span></li>
                                        </ul>
                                    </div>
                                    <div class ="buttonContainer">
                                        <input type="button" value="啟動"/>
                                        <input type="button" value="關閉" style="display:none"/>
                                        <input type="button" value="刪除"/>
                                        <input type="button" value="修改"/>
                                    </div>
                            </div>
                            <div class ="quiz">
                                <form >
                                    <div class = "question"><input type="text" required="required" name="question" placeholder="請輸入問題.." value=""/></div>
                                    <div class = "answerContainer" style="width:500px">
                                        <ul>
                                            <li><input type="radio" class="regular-radio" name="answer" value="A"/>A&nbsp;
                                                 <input type="text" required="required" name="A" placeholder="答案A" /></li>
                                            <li><input type="radio" class="regular-radio"  name="answer" value="B"/>B&nbsp;
                                                 <input type="text" required="required" name="B" placeholder="答案B" /></li>
                                            <li><input type="radio" class="regular-radio"  name="answer" value="C"/>C&nbsp;
                                                <input type="text" required="required" name="C" placeholder="答案C" /></li>
                                            <li><input type="radio" class="regular-radio"  name="answer" value="D"/>D&nbsp;
                                                <input type="text" required="required" name="D"  placeholder="答案D" /></li>
                                        </ul>
                                    </div>
                                    <div class ="buttonContainer">
                                        <input type="button" value="刪除"/>
                                        <input type="submit" value="確認"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div id="AddQuestionBtn" > 新增問題 + </div>
                    </div>
                    
                    <div class="classFunc control" style="display: none">
                        <!--啟動問題-->
						<div>
							<h2>及時測驗</h2>
							<p>問題:XXXXXX 
								<input type="button" value="啟動" >
								<input type="button" value="詳細" >
							</p>
							<p>問題2:XXXXXX 
								<input type="button" value="啟動" >
								<input type="button" value="詳細" >
							</p>
							<p>　　> 詳細資料:XXXXXX <br/>
								　　1.統計圖
							</p>
						</div>	
						<div >
							<h2>訊息</h2>
							<div style="border:2px solid;">
								匿名:剛剛那個是什麼意思?<br/>
								匿名:什麼是sort<br/>
								匿名:這個系統真不錯<br/>
							</div>
						
						</div>
						<div >
							<h2>加分</h2>
							<input type="text"><input type="button" value="send">
						
						</div>
						
                        <!--問題統計-->
                        <!--Message-->
                        <!--加分-->
                    </div>
                    
                    <div class="classFunc student" style="display: none">
                        <!--學生名單-->
						<table border="0" cellpadding="0" cellspacing="0" class="horizontal studentContainer">
							<tr class="BigTitle">
								<th colspan="5">Student Management</th>
							</tr>
							<tr class="title">
								<td>學號</td>
								<td>姓名</td>
								<td>回答紀錄</td>
								<td>發問次數</td>
								<td>刪除學生</td>
							</tr>
							<tr>
								<td>98703005</td>
								<td>張學友</td>
								<td>19/20(正確/總題數)</td>
								<td>20次</td>
								<td>刪除</td>
							</tr>
							
                        </table>
						
						<div class="AddStudent">
							<div class="ui-widget">
								<input type="text"  id="AddStudentText" />
								<span  id="AddStudentName" ></span>
								<input type="button" id="AddStudentBtn" value="新增學生+"/>
							</div>
							
							
						</div>
                        
                    </div>
					
                </div>
            </div>
          
        
        </div>
		
		
	</div>
	<div id="profile" class="clearfix middleArea" style="display:none;" >
		<form id="form1" name="form1" method="post" action="#" onsubmit="return UpdateAccount();">
		
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
				 
			</p>
		</form>
	</div>
</div>

<div class="floor">
    <div>
    	<a class="testBtn">TechTA</a><br/><br/><br/>
    </div>
</div>

<script id="quiz-template" type="text/x-jquery-tmpl">

				<div class ="quiz" index="${index}" qid="${qid}">
					
						<div class = "question">${question}</div>
						<div class = "answerContainer" style="width:500px">
							<ul>
								<li><span class="correctAnswer">{{if correctA}}●{{else}}○{{/if}}</span> <span class="Answer" > ${A}</span></li>
								<li><span class="correctAnswer">{{if correctB}}●{{else}}○{{/if}}</span> <span class="Answer" > ${B}</span></li>
								<li><span class="correctAnswer">{{if correctC}}●{{else}}○{{/if}}</span> <span class="Answer" > ${C}</span></li>
								<li><span class="correctAnswer">{{if correctD}}●{{else}}○{{/if}}</span> <span class="Answer" > ${D}</span></li>
							</ul>
						</div>
						<div class ="buttonContainer">
							<input type="button"class="ActiveQuiz"  value="啟動" style="${openStyle}" />
							<input type="button" class="DeActiveQuiz" value="關閉" style="${closeStyle}"/>
							<input type="button" class="RemoveQuiz" value="刪除"/>
							<input type="button" class="ModifyQuiz" value="修改"/>
						</div>
				</div>
</script>

<script id="quiz-add-template" type="text/x-jquery-tmpl">

			<div class ="quiz" index="${index}" qid="${qid}">
				<form>
					<div class = "question"><input type="text" required="required" name="question" placeholder="請輸入問題.." value="${question}" /></div>
					<div class = "answerContainer" style="width:500px">
						<ul>
							<li><input type="radio" class="regular-radio" name="answer" value="A" {{if correctA}}checked{{/if}}/>A&nbsp;
								 <input type="text" required="required" name="A" placeholder="答案A" value="${A}" /></li>
							<li><input type="radio" class="regular-radio"  name="answer" value="B" {{if correctB}}checked{{/if}}/>B&nbsp;
								 <input type="text" required="required" name="B" placeholder="答案B" value="${B}" /></li>
							<li><input type="radio" class="regular-radio"  name="answer" value="C" {{if correctC}}checked{{/if}}/>C&nbsp;
								<input type="text" required="required" name="C" placeholder="答案C" value="${C}" /></li>
							<li><input type="radio" class="regular-radio"  name="answer" value="D" {{if correctD}}checked{{/if}}/>D&nbsp;
								<input type="text" required="required" name="D" placeholder="答案D" value="${D}" /></li>
						</ul>
					</div>
					<div class ="buttonContainer">
						<!--<input type="button" class="RemoveQuiz" value="刪除"/>-->
						<input type="submit" value="確認"/>
					</div>
				</form>
			</div>
</script>

<script id="student-template" type="text/x-jquery-tmpl">
							<tr>
								<td>${account}</td>
								<td>${name}</td>
								<td>${CQ}/${TQ}/(正確/總題數)</td>
								<td>${TM}次</td>
								<td account=${account} class="RemoveStudentBtn">刪除</td>
							</tr>
</script>

<script type="text/javascript" src="mainMVC.js" ></script>
</body>
</html>
