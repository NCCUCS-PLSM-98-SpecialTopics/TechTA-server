<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>�L���D���</title>

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
            <a id="LogoutBtn"><span id="UserNameSpan">JACK</span> �z�n, �n�X</a>
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
                    
               <div class="box"><h2>�p�������</h2></div>
               <div class="box"><h2>�p����{���]�p</h2></div>
               <div class="box"><h2>�{���y��</h2></div>
               <div class="box"><h2>�t�ε{��</h2></div>
               <div class="box"><h2>�H�u���z</h2></div>
               <div class="box"><h2>�p�����´�P���c</h2></div>
                <!--������
                <div class="hex-row">
                    <div class="hex"><div class="top"></div><div class="middle"><div class="text">�p�������</div></div><div class="bottom"></div></div>
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
                �ҵ{�W��:<input type="text" name="name"/>
                �~��:<select name="year">
                        <option value="98">98</option>
                        <option value="99">99</option>
                        <option value="100">100</option>
                    </select>
                �Ǵ�:<select name="semester">
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                <input type="button" value="����"/>
                <input type="submit" value="�s�W"/>
            </form>
        </div>
        
        <div id="addclass" class="middleArea clearfix" style="display:none;"  >
             <form onsubmit="return false;">
                �Ұ�W��:<input type="text" name="name"/>
                �P��:<select name="week">
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
                <input type="button" value="����"/>
                <input type="submit" value="�s�W"/>
            </form>
        </div>
        
        <div id="class" class="clearfix" style="display:none;"  >
                
        
            <div class="class-menu middleArea" style="">
                <div class="sidebar">
                    <span class="title" >�p�������</span>
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
                        
                        <!--�T����--><div id="messagebox" class="messagebox" placeholder="�ثe�L�s�T��"></div>
                        
                        <!--�o�e��--><input type="text" placeholder="�п�J�T��..." />
                        			<input type="button" value="�e�X"/>

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
                                        <input type="button" value="�Ұ�"/>
                                        <input type="button" value="����" style="display:none"/>
                                        <input type="button" value="�R��"/>
                                        <input type="button" value="�ק�"/>
                                    </div>
                            </div>
                            <div class ="quiz">
                                <form >
                                    <div class = "question"><input type="text" required="required" name="question" placeholder="�п�J���D.." value=""/></div>
                                    <div class = "answerContainer" style="width:500px">
                                        <ul>
                                            <li><input type="radio" class="regular-radio" name="answer" value="A"/>A&nbsp;
                                                 <input type="text" required="required" name="A" placeholder="����A" /></li>
                                            <li><input type="radio" class="regular-radio"  name="answer" value="B"/>B&nbsp;
                                                 <input type="text" required="required" name="B" placeholder="����B" /></li>
                                            <li><input type="radio" class="regular-radio"  name="answer" value="C"/>C&nbsp;
                                                <input type="text" required="required" name="C" placeholder="����C" /></li>
                                            <li><input type="radio" class="regular-radio"  name="answer" value="D"/>D&nbsp;
                                                <input type="text" required="required" name="D"  placeholder="����D" /></li>
                                        </ul>
                                    </div>
                                    <div class ="buttonContainer">
                                        <input type="button" value="�R��"/>
                                        <input type="submit" value="�T�{"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div id="AddQuestionBtn" > �s�W���D + </div>
                    </div>
                    
                    <div class="classFunc control" style="display: none">
                        <!--�Ұʰ��D-->
						<div>
							<h2>�ήɴ���</h2>
							<p>���D:XXXXXX 
								<input type="button" value="�Ұ�" >
								<input type="button" value="�Բ�" >
							</p>
							<p>���D2:XXXXXX 
								<input type="button" value="�Ұ�" >
								<input type="button" value="�Բ�" >
							</p>
							<p>�@�@> �ԲӸ��:XXXXXX <br/>
								�@�@1.�έp��
							</p>
						</div>	
						<div >
							<h2>�T��</h2>
							<div style="border:2px solid;">
								�ΦW:��診�ӬO����N��?<br/>
								�ΦW:����Osort<br/>
								�ΦW:�o�Өt�ίu����<br/>
							</div>
						
						</div>
						<div >
							<h2>�[��</h2>
							<input type="text"><input type="button" value="send">
						
						</div>
						
                        <!--���D�έp-->
                        <!--Message-->
                        <!--�[��-->
                    </div>
                    
                    <div class="classFunc student" style="display: none">
                        <!--�ǥͦW��-->
						<table border="0" cellpadding="0" cellspacing="0" class="horizontal studentContainer">
							<tr class="BigTitle">
								<th colspan="5">Student Management</th>
							</tr>
							<tr class="title">
								<td>�Ǹ�</td>
								<td>�m�W</td>
								<td>�^������</td>
								<td>�o�ݦ���</td>
								<td>�R���ǥ�</td>
							</tr>
							<tr>
								<td>98703005</td>
								<td>�i�Ǥ�</td>
								<td>19/20(���T/�`�D��)</td>
								<td>20��</td>
								<td>�R��</td>
							</tr>
							
                        </table>
						
						<div class="AddStudent">
							<div class="ui-widget">
								<input type="text"  id="AddStudentText" />
								<span  id="AddStudentName" ></span>
								<input type="button" id="AddStudentBtn" value="�s�W�ǥ�+"/>
							</div>
							
							
						</div>
                        
                    </div>
					
                </div>
            </div>
          
        
        </div>
		
		
	</div>
	<div id="profile" class="clearfix middleArea" style="display:none;" >
		<form id="form1" name="form1" method="post" action="#" onsubmit="return UpdateAccount();">
		
			<p>   account�G<input type="text" name="account" id="U_account" />   </p>
			<p>   password�G<input type="text" name="password" id="U_password" />   </p>
			<p>   name�G<input type="text" name="name" id="U_name" />   </p>
			<p>   email�G<input type="text" name="email" id="U_email" />   </p>
			<p>   department�G<input type="text" name="department" id="U_department" />   </p>
			<p>   role�G
				<select name="role"  id="U_role" >
					<option value="teacher">�Ѯv</option>
					<option value="student">�ǥ�</option>
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
								<li><span class="correctAnswer">{{if correctA}}��{{else}}��{{/if}}</span> <span class="Answer" > ${A}</span></li>
								<li><span class="correctAnswer">{{if correctB}}��{{else}}��{{/if}}</span> <span class="Answer" > ${B}</span></li>
								<li><span class="correctAnswer">{{if correctC}}��{{else}}��{{/if}}</span> <span class="Answer" > ${C}</span></li>
								<li><span class="correctAnswer">{{if correctD}}��{{else}}��{{/if}}</span> <span class="Answer" > ${D}</span></li>
							</ul>
						</div>
						<div class ="buttonContainer">
							<input type="button"class="ActiveQuiz"  value="�Ұ�" style="${openStyle}" />
							<input type="button" class="DeActiveQuiz" value="����" style="${closeStyle}"/>
							<input type="button" class="RemoveQuiz" value="�R��"/>
							<input type="button" class="ModifyQuiz" value="�ק�"/>
						</div>
				</div>
</script>

<script id="quiz-add-template" type="text/x-jquery-tmpl">

			<div class ="quiz" index="${index}" qid="${qid}">
				<form>
					<div class = "question"><input type="text" required="required" name="question" placeholder="�п�J���D.." value="${question}" /></div>
					<div class = "answerContainer" style="width:500px">
						<ul>
							<li><input type="radio" class="regular-radio" name="answer" value="A" {{if correctA}}checked{{/if}}/>A&nbsp;
								 <input type="text" required="required" name="A" placeholder="����A" value="${A}" /></li>
							<li><input type="radio" class="regular-radio"  name="answer" value="B" {{if correctB}}checked{{/if}}/>B&nbsp;
								 <input type="text" required="required" name="B" placeholder="����B" value="${B}" /></li>
							<li><input type="radio" class="regular-radio"  name="answer" value="C" {{if correctC}}checked{{/if}}/>C&nbsp;
								<input type="text" required="required" name="C" placeholder="����C" value="${C}" /></li>
							<li><input type="radio" class="regular-radio"  name="answer" value="D" {{if correctD}}checked{{/if}}/>D&nbsp;
								<input type="text" required="required" name="D" placeholder="����D" value="${D}" /></li>
						</ul>
					</div>
					<div class ="buttonContainer">
						<!--<input type="button" class="RemoveQuiz" value="�R��"/>-->
						<input type="submit" value="�T�{"/>
					</div>
				</form>
			</div>
</script>

<script id="student-template" type="text/x-jquery-tmpl">
							<tr>
								<td>${account}</td>
								<td>${name}</td>
								<td>${CQ}/${TQ}/(���T/�`�D��)</td>
								<td>${TM}��</td>
								<td account=${account} class="RemoveStudentBtn">�R��</td>
							</tr>
</script>

<script type="text/javascript" src="mainMVC.js" ></script>
</body>
</html>
