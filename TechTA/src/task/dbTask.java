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
										result.getString("active"),null,null,null
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
	

	public  List<ClassModel> GetClassByClassid(String clid){
		
		List<ClassModel> modelList = new  ArrayList<ClassModel>();
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
										result.getString("active"),null,null,null
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
		List<String> AccountList = new  ArrayList<String>();
		List<UserModel> modelList = new  ArrayList<UserModel>();
		
		
		String queryStr = "SELECT `account` FROM `enroll` WHERE `co_id` = ?";
		String[] strArray = {coid};
		ResultSet result = db.SelectTable(queryStr, strArray);
		try {
			while(result.next()) 
			{ 
				AccountList.add(result.getString("account"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		db.Close();
		
		for(String account:AccountList){
			modelList.add(GetUser(account));
		}
		
		return modelList;
		
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

	
//-------question---
	
	public  List<QuizModel> GetQuizByClassid(String clid){
		
		List<QuizModel> modelList = new  ArrayList<QuizModel>();
		QuizModel model = null;
		String queryStr = "SELECT `q_id`, `question`, `correct_answer`, `choices`, `active` FROM `quiz` WHERE `cl_id` = ?";
		String[] strArray = {clid};
		ResultSet result = db.SelectTable(queryStr, strArray);
		try {
			while(result.next()) 
			{ //(`cl_id`, `cl_name`, `week`, `active`, `co_id`) 
				model = new QuizModel(result.getString("q_id"), 
						result.getString("question"),
						result.getString("correct_answer"),
						result.getString("choice"),
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
	
	
	public  String AddQuizToClass(QuizModel model){
		String userSql = "INSERT INTO `quiz`(`q_id`, `question`, `correct_answer`, `choices`, `active`, `cl_id`) VALUES (?,?,?,?,?,?) ";
		String[] strArray = {model.getQid(),
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

		
}

