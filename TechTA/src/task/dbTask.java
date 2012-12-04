package task;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;

public class dbTask {
	
	static dbTask instance = null;
	jdbcmysql db = null;
	public dbTask(){
			db = new jdbcmysql();
	}

	public static dbTask getInstance(){
		if(instance != null){
			instance = new dbTask();
		}
		return instance;
	}

	public  UserModel GetUser(String Account){
		
		UserModel model = null;
		String queryStr = "SELECT `account` FROM `user` WHERE account = '?'";
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
		return null;
	}
	

	public  int UpdateAccount(UserModel model){
		
		//UserModel model = null;
		String queryStr = "UPDATE  `tech_ta`.`user` SET  "+
						   "`name` =  '?'"+
						   ",`email` =  '?'"+
						   ",`department` =  '?'"+
						   " WHERE  `user`.`account` =  '?';'";
		
		String[] strArray = {model.getName(),
				             model.getEmail(),
				             model.getDepartment(),
				             model.getAccount()};
		
		int result = db.ChangeData(queryStr, strArray);
		
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
		
		if(result==1)return 0 ;  //success
		else return 1;
		
	}
	
	
	
	
}

