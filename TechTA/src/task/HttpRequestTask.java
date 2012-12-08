package task;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import Enum.RequestMethod;

public class HttpRequestTask {

	
	public static String execute(URL ul, RequestMethod method){
		String result ="";
		
		  try{
		        //Java必須使用java.net套件 所以按手冊會使用URL、HttpURLConnecion等class
		        //URL ul = new URL("http://XXX_soap_ajax.asp");
		        URLConnection   connection   =   ul.openConnection(); 
		        HttpURLConnection   uc   =   (HttpURLConnection)   connection;       
		        //做好 HttpURLConnection class的instance即可做httprequest
		        //但要設定以下的 環境參數
		        uc.setRequestMethod(method.toString());  //POST 的重要參數
		        uc.setRequestProperty ( "Connection", "Keep-Alive" ) ; 
		        uc.setRequestProperty ( "Cache-Control", "no-cache" ) ;    
		        //POST 的重要參數
		        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        //如果有做cookie測試 java 要使用COOKIE不能用http_cookie
		        uc.setRequestProperty("COOKIE", "william");  
		        //HttpURLConnection如果要進行發送 跟讀取回傳 就必須設定以下兩個property為true
		        uc.setDoOutput(true);  
		        uc.setDoInput(true);   
		        //POST參數必須這樣寫 sample為XMLDOC
		        String xml = "XMLDOC=" +"XML內容";
		        byte[] bs = new String(xml).getBytes();    
		        //HttpURLConnection做連線,必須connect、 outputstream一氣喝成 否則資料會傳不到.asp程式內
		        uc.connect();        
		        OutputStream om = uc.getOutputStream();
		        om.write(bs);
		        om.flush();
		        om.close();
		        //寫入參數資料close完後再open inputstream
		        //sample這裡的問題是用bs接收並沒有考慮回傳的資料大小..有需要人請自行再改寫
		        InputStream  im = uc.getInputStream();       
		        im.read(bs);
		        System.out.println(new String(bs));
		        result = new String(bs);
		        im.close();
		        uc.disconnect();
		     }catch (Exception e){System.out.println("Something wrong"+e.getMessage());}
		  
		  return result;
		
	}
}
