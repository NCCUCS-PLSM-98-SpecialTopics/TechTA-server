package task;
import java.sql.*;


public class jdbc2 {
	public static void execute ()
	{
	try{
	Class.forName("com.mysql.jdbc.Driver");

	Connection con = DriverManager.getConnection("jdbc:mysql://140.119.164.163:3306/test","techta","DXGwxYjGuD6VuJz8");


	System.out.println("資料庫連線成功");

	}catch(ClassNotFoundException e){

	System.out.println("找不到連線類別檔案");



	}catch(SQLException e){

	System.out.println("資料庫無法連線!!!");
	System.out.println("請檢查帳號及密碼是否有誤，");
	System.out.println("或者mysql服務是否關閉。");
	}
	}
}