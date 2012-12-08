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
		if(result==1)return 0 ;  //success
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
		if(result==1)return 0 ;  //success
		else return 1;
		
	}
	
	
	

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
										result.getString("active"),null
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
	
	
}

