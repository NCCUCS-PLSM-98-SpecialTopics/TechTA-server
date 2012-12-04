package task; 

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 

public class jdbcmysql { 
  private PrintWriter out =null;
  private Connection con = null; //Database objects //連接object 
  private Statement stat = null; //執行,傳入之sql為完整字串 
  private ResultSet rs = null; //結果集 
  private PreparedStatement pst = null; 
  //執行,傳入之sql為預儲之字申,需要傳入變數之位置 
  
  private String dropdbSQL = "DROP TABLE User "; 
    private String createdbSQL = "CREATE TABLE User (" + 
    "    id     INTEGER " + 
    "  , name    VARCHAR(20) " + 
    "  , passwd  VARCHAR(20))"; 
  
  private String insertdbSQL = "insert into User(id,name,passwd) " + 
      "select ifNULL(max(id),0)+1,?,? FROM User"; 
  
  private String selectSQL = "select * from User "; 
  
  public jdbcmysql(PrintWriter out2) 
  { 
	this.out = out2;
    try { 
      Class.forName("com.mysql.jdbc.Driver"); 
      //註冊driver 
      //con = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5", "root","0000"); 
      con = DriverManager.getConnection("jdbc:mysql://140.119.164.163/test?useUnicode=true&characterEncoding=Big5", "techta","0000"); 
      
      
      //取得connection

//jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
//localhost是主機名,test是database名
//useUnicode=true&characterEncoding=Big5使用的編碼 
      
    } 
    catch(ClassNotFoundException e) 
    { 
    	this.out.println("DriverClassNotFound :"+e.toString()); 
    }//有可能會產生sqlexception 
    catch(SQLException x) { 
    	this.out.println("Exception :"+x.toString()); 
    } 
    
  } 
  
  
  public jdbcmysql() 
  { 
	  try {
		this.out =   new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"));
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    try { 
      Class.forName("com.mysql.jdbc.Driver"); 
      //註冊driver 
      con = DriverManager.getConnection("jdbc:mysql://140.119.164.163/test?useUnicode=true&characterEncoding=Big5", "techta","0000"); 
    } 
    catch(ClassNotFoundException e) 
    { 
    	this.out.println("DriverClassNotFound :"+e.toString()); 
    }//有可能會產生sqlexception 
    catch(SQLException x) { 
    	this.out.println("Exception :"+x.toString()); 
    } 
    
  } 
  
  //建立table的方式 
  //可以看看Statement的使用方式 
  public void createTable() 
  { 
    try 
    { 
      stat = con.createStatement(); 
      stat.executeUpdate(createdbSQL); 
    } 
    catch(SQLException e) 
    { 
    	this.out.println("CreateDB Exception :" + e.toString()); 
    } 
    finally 
    { 
      Close(); 
    } 
  } 
  //新增資料 
  //可以看看PrepareStatement的使用方式 
  public void insertTable( String name,String passwd) 
  { 
    try 
    { 
      pst = con.prepareStatement(insertdbSQL); 
      
      pst.setString(1, name); 
      pst.setString(2, passwd); 
      pst.executeUpdate(); 
    } 
    catch(SQLException e) 
    { 
    	this.out.println("InsertDB Exception :" + e.toString()); 
    } 
    finally 
    { 
      Close(); 
    } 
  } 
  
  //新增資料 
  //可以看看PrepareStatement的使用方式 
  public int ChangeData( String sql ,String [] data ) 
  { 
    try 
    { 
    	
		/*
		String userSql = "INSERT INTO `user`(`account`, `password`, `name`, `email`, `department`, `role`, `chatid`) VALUES (?,?,?,?,?,?,?)";
		String classSql = "INSERT INTO `class`(`cl_id`, `cl_name`, `week`, `active`, `co_id`) VALUES (?,?,?,?,?)";
		String courseSql = "INSERT INTO `course`(`co_id`, `c_name`, `year`, `semester`) VALUES (?,?,?,?)";
		String enrollSql = "INSERT INTO `enroll`(`account`, `co_id`) VALUES (?,?)";
		String messageSql = "INSERT INTO `message`(`m_id`, `content`, `cl_id`, `account`) VALUES (?,?,?,?)";
		String quizSql = "INSERT INTO `quiz`(`q_id`, `question`, `correct_answer`, `answer`, `active`) VALUES (?,?,?,?,?)";
		String takequizSql = "INSERT INTO `takequiz`(`account`, `q_id`, `answer`) VALUES (?,?,?)";
		 
		*/
		
      pst = con.prepareStatement(sql); 
      
      int count = 1;
      for(String adata : data){
    	  pst.setString(count, adata); 
    	  count++;
      }
      int result = pst.executeUpdate(); 
      return result;  //return 1  success!
    } 
    catch(SQLException e) 
    { 
    	this.out.println("InsertDB Exception :" + e.toString()); 
    } 
    finally 
    { 
      Close(); 
      return -1;
    } 
  } 
  //刪除Table, 
  //跟建立table很像 
  public void dropTable() 
  { 
    try 
    { 
      stat = con.createStatement(); 
      stat.executeUpdate(dropdbSQL); 
    } 
    catch(SQLException e) 
    { 
    	this.out.println("DropDB Exception :" + e.toString()); 
    } 
    finally 
    { 
      Close(); 
    } 
  } 
  //查詢資料 
  //可以看看回傳結果集及取得資料方式 
  public ResultSet SelectTable(String queryStr, String [] data) 
  { 
    try 
    { 
      pst = con.prepareStatement(queryStr); 
      int count = 1;
      for(String adata : data){
    	  pst.setString(count, adata); 
    	  count++;
      }
      rs = pst.executeQuery(); 
      
      return rs;
   
    } 
    catch(SQLException e) 
    { 
    	this.out.println("DropDB Exception :" + e.toString()); 
    } 
    finally 
    { 
      Close(); 
      return null;
    } 
  } 
  

  public void query(String queryStr, String [] data) 
  { 
	  
	  //SELECT `account` FROM `user` WHERE account = '98703005'
    try 
    { 
      stat = con.createStatement(); 
      rs = stat.executeQuery(selectSQL); 
      System.out.println("ID\t\tName\t\tPASSWORD"); 
      while(rs.next()) 
      { 
    	  this.out.println(rs.getInt("id")+"\t\t"+ 
            rs.getString("name")+"\t\t"+rs.getString("passwd")); 
      } 
    } 
    catch(SQLException e) 
    { 
    	this.out.println("DropDB Exception :" + e.toString()); 
    } 
    finally 
    { 
      Close(); 
    } 
  } 
  //完整使用完資料庫後,記得要關閉所有Object 
  //否則在等待Timeout時,可能會有Connection poor的狀況 
  private void Close() 
  { 
    try 
    { 
      if(rs!=null) 
      { 
        rs.close(); 
        rs = null; 
      } 
      if(stat!=null) 
      { 
        stat.close(); 
        stat = null; 
      } 
      if(pst!=null) 
      { 
        pst.close(); 
        pst = null; 
      } 
    } 
    catch(SQLException e) 
    { 
    	this.out.println("Close Exception :" + e.toString()); 
    } 
  } 
  

  public void execute(PrintWriter out) 
  { 
	this.out = out;
    //測看看是否正常 
   // jdbcmysql test = new jdbcmysql(); 
    //test.dropTable(); 
    //test.createTable(); 
    //test.insertTable("yku", "12356"); 
   // test.insertTable("yku2", "7890"); 
   // test.SelectTable(); 
  
  } 
}