package task;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5 {

	public static String encode(String str){
		  //確定計算方法
        MessageDigest md5 = null;
        String newstr="";
        BASE64Encoder base64en = new BASE64Encoder();
        
		try {
			md5 = MessageDigest.getInstance("MD5");
			newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //加密後的字符串
        
		
        return newstr;
		
		
	}

}
