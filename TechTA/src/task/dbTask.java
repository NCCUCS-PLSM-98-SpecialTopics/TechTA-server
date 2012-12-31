package task;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import model.*;

public class dbTask {
	
	static dbTask instance = null;
	jdbcmysql db = null;
	public dbTask(){
			db = new jdbcmysql();
	}

	public static dbTask getInstance(){
		if(instance == null){
			instance = new dbTask();
		}
		return instance;
	}

	public  UserModel GetUser(String Account){
		
		UserModel model = null;
		String queryStr = "SELECT * FROM `user` WHERE account = ?";
		String[] strArray = {Account};
		ResultSet result = db.SelectTable(queryStr, strArray);
		try {
			while(result.next()) 
			{ 
				model = new UserModel(result.getString("account"),
										result.getString("password"),
										result.getString("name"),
										result.getString("email"),
										result.getString("department"),
										result.getString("role"),
										result.getString("chatid") );
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		db.Close();
		return model;
	}
	

	public  List<UserModel> GetAllUser(){
		
		
		String queryStr = "SELECT * FROM `user`";
		List<UserModel> list = new ArrayList<UserModel>();
		ResultSet result = db.SelectTable(queryStr, null);
		try {
			while(result.next()) 
			{ 
				list.add( new UserModel(result.getString("account"),
										result.getString("password"),
										result.getString("name"),
										result.getString("email"),
										result.getString("department"),
										result.getString("role"),
										result.getString("chatid") ));
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		db.Close();
		return list;
	}
	
	public  int UpdateAccount(UserModel model){
		
		//UserModel model = null;
		String queryStr = "UPDATE  `tech_ta`.`user` SET  "+
						   "`name` =  ?"+
						   ",`email` =  ?"+
						   ",`department` =  ?"+
						   " WHERE  `user`.`account` =  ?";
		
		String[] strArray = {model.getName(),
				             model.getEmail(),
				             model.getDepartment(),
				             model.getAccount()};
		
		int result = db.ChangeData(queryStr, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
		
	}
	
	public  int CreateAccount(UserModel model){
		
		String userSql = "INSERT INTO `user`(`account`, `password`, `name`, `email`, `department`, `role`, `chatid`) VALUES (?,?,?,?,?,?,?)";
			
		String[] strArray = {model.getAccount(),
							 model.getPassword(),
							 model.getName(),
				             model.getEmail(),
				             model.getDepartment(),
				             model.getRole(),
				             model.getChatid()
				             };
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
		
	}
	
	
//-----------------Course------------------------------------------

	public  List<CourseModel> GetCourseByAccount(String Account){
		List<CourseModel> courseList = new ArrayList<CourseModel>();
		CourseModel course = null;
		String queryStr = "SELECT * FROM `course` WHERE co_id in (SELECT co_id FROM `enroll` WHERE account = ?)";
		String[] strArray = {Account};
		ResultSet result = db.SelectTable(queryStr, strArray);
		try {
			while(result.next()) 
			{ //co_id c_name year semester
				course = new CourseModel(result.getString("co_id"),
										result.getString("c_name"),
										result.getString("year"),
										result.getString("semester"),null );
				courseList.add(course);
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		db.Close();
		
		for(CourseModel COmodel: courseList){
			List<ClassModel> classList = this.GetClassByCourse(COmodel.getCoid());
			
			COmodel.setClasses(classList);
			
		}
		
		
		
		
		
		return courseList;
	}
	
	
	public  String CreateCourse(CourseModel model){
		
		String userSql = "INSERT INTO `course`(`c_name`, `year`, `semester`) VALUES (?,?,?)";
		
		String[] strArray = {model.getName(),model.getYear(), model.getSemester() };
		
		String result = db.ChangeDataAndGetKey(userSql, strArray);

		db.Close();

		return result;// if null then error
	}


	public  int UpdateCourse(CourseModel model){
		String userSql = "UPDATE `course` SET `c_name`=?,`year`=?,`semester`=? WHERE `co_id`=?";
		
		String[] strArray = {model.getName(),model.getYear(), model.getSemester() ,model.getCoid()};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}
	
	public  int RemoveCourse(String coid){
		
		
		/// remove class by course

		
		String userSql = "DELETE FROM `course` WHERE `co_id`=? ";
		
		String[] strArray = {coid};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}
	
//-----------------Class------------------------------------------

	public  List<ClassModel> GetClassByCourse(String coid){
		
		List<ClassModel> modelList = new  ArrayList<ClassModel>();
		ClassModel model = null;
		String queryStr = "SELECT * FROM `class` WHERE co_id = ?";
		String[] strArray = {coid};
		ResultSet result = db.SelectTable(queryStr, strArray);
		try {
			while(result.next()) 
			{ //(`cl_id`, `cl_name`, `week`, `active`, `co_id`) 
				model = new ClassModel(result.getString("cl_id"),
										result.getString("cl_name"),
										result.getString("week"),
										result.getString("active"),
										result.getString("roomid"),
										result.getString("co_id"),null,null
										);
				modelList.add(model);
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		db.Close();
		return modelList;
	}
	

	public  ClassModel GetClassByClassid(String clid){
		
		
		ClassModel model = null;
		String queryStr = "SELECT * FROM `class` WHERE cl_id = ?";
		String[] strArray = {clid};
		ResultSet result = db.SelectTable(queryStr, strArray);
		try {
			while(result.next()) 
			{ //(`cl_id`, `cl_name`, `week`, `active`, `co_id`) 
				model = new ClassModel(result.getString("cl_id"),
										result.getString("cl_name"),
										result.getString("week"),
										result.getString("active"),
										result.getString("roomid"),
										result.getString("co_id"),null,null
														);
				
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		db.Close();
		return model;
	}
	
	
	public  String CreateClassFromCourse(ClassModel model){
		String userSql = "INSERT INTO `class`(`cl_name`, `week`, `active`, `co_id`) VALUES (?,?,?,?)";
		
		String[] strArray = {model.getName(),model.getWeek(),model.getActive(),model.getParentCourseId()};
		
		String result = db.ChangeDataAndGetKey(userSql, strArray);

		db.Close();

		return result;// if null then error
	
	}

	public  int UpdateClass(ClassModel model){
		
		String userSql = "UPDATE `class` SET `cl_name`=?,`week`=?,`active`=?  WHERE  `cl_id`=? ";
		
		String[] strArray = {model.getName(),model.getWeek(),model.getActive(),model.getParentCourseId()};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}
	
	public  int ActiveClass(String clid,String roomid){
		
		String userSql = "UPDATE `class` SET `active`='1' , `roomid`=? WHERE  `cl_id`=? ";
		
		String[] strArray = {roomid,clid};
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}

	
	public  int DeActiveClass(String clid){
		
		String userSql = "UPDATE `class` SET `active`='0'  , `roomid`=NULL WHERE  `cl_id`=? ";
		
		String[] strArray = {clid};
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}
	
	public  int RemoveClass(String clid){
		
		
		String userSql = "DELETE FROM `class` WHERE `cl_id`=? ";
		
		String[] strArray = {clid};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	
	}
	

	public  int RemoveAllClassFromCourse(String coid){
		
		
		String userSql = "DELETE FROM `class` WHERE   `co_id`=? ";
		
		String[] strArray = {coid};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	
	}

//----- Enroll ----------
	public  List<UserModel> GetAllUserByCourse(String coid){

		List<UserModel> list = new ArrayList<UserModel>();
		String queryStr = "SELECT * FROM `enroll` natural join `user`   WHERE `co_id` = ?";
		String[] strArray = {coid};
		
		ResultSet result = db.SelectTable(queryStr, strArray);
		try {
			while(result.next()) 
			{ 
				list.add( new UserModel(result.getString("account"),
										result.getString("password"),
										result.getString("name"),
										result.getString("email"),
										result.getString("department"),
										result.getString("role"),
										result.getString("chatid") ));
			}
	
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		db.Close();
		
		
		
		return list;
		
	}
	public  int CheckUserToCourse(String coid, String account){

		boolean AlreadyInThisClass = false;
		String queryStr = "SELECT `account` FROM `enroll` WHERE `co_id` = ? AND `account` = ?";
		String[] strArray = {coid,account};
		ResultSet result = db.SelectTable(queryStr, strArray);
		try {
			while(result.next()) 
			{ 
				AlreadyInThisClass = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		db.Close();

		if(!AlreadyInThisClass){
			if(this.GetUser(account) != null){
				return 0;
			}else{
				return 2;
			}
		}else{
			return 1;
		}
		
		
	}
	public  int AddUserToCourse(String account, String coid){
		String userSql = "INSERT INTO `enroll`(`account`, `co_id`) VALUES (?,?) ";
		String[] strArray = {account,coid};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}
	public  int RemoveUserFromCourse(String account, String coid){
		String userSql = "DELETE FROM `enroll` WHERE `account`= ? AND `co_id` = ? ";
		String[] strArray = {account,coid};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}

	
//-------Quiz---
	
	public  List<QuizModel> GetQuizByClassid(String clid){
		
		List<QuizModel> modelList = new  ArrayList<QuizModel>();
		QuizModel model = null;
		String queryStr = "SELECT `q_id`, `question`, `correct_answer`, `choices`, `active`, `cl_id` FROM `quiz` WHERE `cl_id` = ?";
		String[] strArray = {clid};
		ResultSet result = db.SelectTable(queryStr, strArray);
		try {
			while(result.next()) 
			{ //(`cl_id`, `cl_name`, `week`, `active`, `co_id`) 
				model = new QuizModel(result.getString("q_id"), 
						result.getString("question"),
						result.getString("correct_answer"),
						result.getString("choices"),
						result.getString("active"),
						result.getString("cl_id"));
				modelList.add(model);
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		db.Close();
		return modelList;
	}
	public  QuizModel GetQuizByqid(String qid){
		
		List<QuizModel> modelList = new  ArrayList<QuizModel>();
		QuizModel model = null;
		String queryStr = "SELECT `q_id`, `question`, `correct_answer`, `choices`, `active`, `cl_id` FROM `quiz` WHERE `q_id` = ?";
		String[] strArray = {qid};
		ResultSet result = db.SelectTable(queryStr, strArray);
		try {
			while(result.next()) 
			{ //(`cl_id`, `cl_name`, `week`, `active`, `co_id`) 
				model = new QuizModel(result.getString("q_id"), 
						result.getString("question"),
						result.getString("correct_answer"),
						result.getString("choices"),
						result.getString("active"),
						result.getString("cl_id"));
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		db.Close();
		return model;
	}
	
	
	public  String AddQuizToClass(QuizModel model){
		String userSql = "INSERT INTO `quiz`(`question`, `correct_answer`, `choices`, `active`, `cl_id`) VALUES (?,?,?,?,?) ";
		String[] strArray = {
							model.getQuestion(),
							model.getCorrectAnswer(),
							model.getChoice(),
							model.getActive(),
							model.getClid()};
		
		String result = db.ChangeDataAndGetKey(userSql, strArray);

		db.Close();

		return result;// if null then error
	}

	public  int UpdateQuiz(QuizModel model){
		String userSql = "UPDATE `quiz` SET `question`=?,`correct_answer`=?,`choices`=?,`active`=? WHERE `q_id`=?";
		String[] strArray = {
							model.getQuestion(),
							model.getCorrectAnswer(),
							model.getChoice(),
							model.getActive(),
							model.getQid()};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}

	public  int ActiveQuiz(String qid, String active){
		String userSql = "UPDATE `quiz` SET `active`=? WHERE `q_id`=?";
		String[] strArray = {active,qid};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}
	
	public  int RemoveQuiz(String qid){
		String userSql = "DELETE FROM `quiz` WHERE `q_id`=?";
		String[] strArray = {qid};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}

	public  int RemoveQuizByClass(String clid){
		String userSql = "DELETE FROM `quiz` WHERE `cl_id`=?";
		String[] strArray = {clid};
		
		int result = db.ChangeData(userSql, strArray);

		db.Close();
		if(result == 1)return 0 ;  //success
		else return 1;
	}

	public  String AddTakeQuiz(String qid, String account, String answer){
		String userSql = "INSERT INTO `tech_ta`.`takequiz` (`account`, `q_id`, `answer`) VALUES (?, ?, ?); ";
		String[] strArray = {account, qid , answer};
		
		String result = db.ChangeDataAndGetKey(userSql, strArray);

		db.Close();

		return result;// if null then error
	}
	
//-------message---
	
		public  List<MessageModel> GetMessageByClassid(String clid){
			
			List<MessageModel> modelList = new  ArrayList<MessageModel>();
			MessageModel model = null;
			String queryStr = "SELECT `m_id`, `content`, `cl_id`, `account`, `time` FROM `message` WHERE cl_id = ?";
			String[] strArray = {clid};
			ResultSet result = db.SelectTable(queryStr, strArray);
			try {
				while(result.next()) 
				{ 
					model = new MessageModel(result.getString("m_id"),
											result.getString("content"),
											result.getString("cl_id"),
											result.getString("account"),
											result.getString("time")
											);
					modelList.add(model);
				}
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			db.Close();
			return modelList;
		}
		
		
		public  String AddMessage(MessageModel model){
			String userSql = "INSERT INTO `message`(`m_id`, `content`, `cl_id`, `account`, `time`) VALUES (?,?,?,?)";
			String[] strArray = {model.getMid(),model.getContent(),model.getClid(),model.getAccount()};
			
			String result = db.ChangeDataAndGetKey(userSql, strArray);

			db.Close();

			return result;// if null then error
		}

		public  int RemoveMessageByClass(String clid){
			String userSql = "DELETE FROM `message` WHERE `cl_id` = ?";
			String[] strArray = {clid};
			
			int result = db.ChangeData(userSql, strArray);

			db.Close();
			if(result == 1)return 0 ;  //success
			else return 1;
		}
// ----------GET ALL STUDENT Of  A Course ----------
		
		public  List<Map<String, String>> GetAllStudentInfoByCourse(String coid){
			String queryStr = 
		" SELECT P.account,P.name ,ifnull(Q.TQ, 0) TQ ,ifnull(Q.CQ, 0) CQ,ifnull(M.TM, 0) TM FROM" +
		" 	(SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join ("+
		" 		SELECT  * FROM user where role = 'student'"+
		" 		)c where enroll.co_id = ?"+
		" 	)P	"+
		" left join"+
		" 	(SELECT account, count(m_id) TM FROM message NATURAL  join ("+
		" 		SELECT * FROM class NATURAL  join ("+
		" 			SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join ("+
		" 				SELECT  * FROM user"+
		" 			)c where enroll.co_id = ?"+
		" 		) b  "+
		" 	)a GROUP BY account) M"+
		" ON P.account = M.account"+
		" left join"+
		"  	(SELECT account ,count(q_id) CQ,TQ ,question  FROM("+
		" 		SELECT *,count(q_id) TQ FROM takequiz   NATURAL join ("+
		" 			SELECT cl_id, q_id,correct_answer, question, account, name FROM quiz NATURAL  join ("+
		" 				SELECT * FROM class NATURAL  join ("+
		" 					SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join ("+
		" 						SELECT  * FROM user"+
		" 					)c where enroll.co_id = ?"+
		" 				) b  "+
		" 			)a"+
		" 		)z GROUP BY z.account HAVING answer = correct_answer"+
		" 	)x GROUP BY account ) Q"+
		" ON P.account = Q.account";
			
			String[] strArray = {coid,coid,coid};
			ResultSet result = db.SelectTable(queryStr, strArray);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			try {
				while(result.next()) 
				{ 
					Map<String, String> map = new HashMap<String,String>();
					map.put("account", result.getString("account"));
					map.put("name", result.getString("name"));
					map.put("TQ", result.getString("TQ"));
					map.put("CQ", result.getString("CQ"));
					map.put("TM", result.getString("TM"));
					list.add(map);
				}
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				list = null;
				e.printStackTrace();
			} 
			db.Close();
			return list;
		}

		
}

