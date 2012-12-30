package task;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
* Apache HttpClient 4.x 使用 GET, POST 查詢網頁的範例
*
* @author werdna at http://werdna1222coldcodes.blogspot.com/
*/

public class HttpClientRequest extends DefaultHttpClient {

    public static void test(String[] args) throws IOException {

        DefaultHttpClient demo = new DefaultHttpClient();
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        // Get Request Example，取得 google 查詢 httpclient 的結果
        HttpGet httpGet = new HttpGet("http://www.google.com.tw/search?q=httpclinet");
        HttpResponse response = demo.execute(httpGet);
        String responseString = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 如果回傳是 200 OK 的話才輸出
            System.out.println(responseString);
        } else {
            System.out.println(response.getStatusLine());
        }

        // Post Request Example，查詢台大圖書館書籍
        ArrayList<NameValuePair> pairList = new ArrayList<NameValuePair>();
        pairList.add(new BasicNameValuePair("searchtype", "t"));
        pairList.add(new BasicNameValuePair("searchscope", "keyword"));
        pairList.add(new BasicNameValuePair("searcharg", "Head First Java"));
        pairList.add(new BasicNameValuePair("SORT", "D"));

        HttpPost httpPost = new HttpPost("http://tulips.ntu.edu.tw:1081/search*cht/a?");
        StringEntity entity = new StringEntity(URLEncodedUtils.format(pairList, "UTF-8"));
        httpPost.setEntity(entity);
        response = demo.execute(httpPost);
        responseString = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 如果回傳是 200 OK 的話才輸出
            System.out.println(responseString);
        } else {
            System.out.println(response.getStatusLine());
        }
        
    }
    
    public static String Get(String url) throws IOException{
    	
    	DefaultHttpClient demo = new DefaultHttpClient();
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        // Get Request Example，取得 google 查詢 httpclient 的結果
        //HttpGet httpGet = new HttpGet("http://www.google.com.tw/search?q=httpclinet");
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = demo.execute(httpGet);
        String responseString = EntityUtils.toString(response.getEntity());
        
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 如果回傳是 200 OK 的話才輸出
            System.out.println(responseString);
        } else {
            System.out.println(response.getStatusLine());
        }
        
		return responseString;
    }
    
    public static String Post(String url, ArrayList<NameValuePair> pairList) throws IOException{
    	
    	DefaultHttpClient demo = new DefaultHttpClient();
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        
        //ArrayList<NameValuePair> pairList = new ArrayList<NameValuePair>();
        //pairList.add(new BasicNameValuePair("searchtype", "t"));
        //pairList.add(new BasicNameValuePair("searchscope", "keyword"));
        //pairList.add(new BasicNameValuePair("searcharg", "Head First Java"));
        //pairList.add(new BasicNameValuePair("SORT", "D"));
        
        //HttpPost httpPost = new HttpPost("http://tulips.ntu.edu.tw:1081/search*cht/a?");
        HttpPost httpPost = new HttpPost(url);
        if(pairList != null){
	        StringEntity entity = new StringEntity(URLEncodedUtils.format(pairList, "UTF-8"));
	        httpPost.setEntity(entity);
        }else{
        	
            ArrayList<NameValuePair> pairList2 = new ArrayList<NameValuePair>();
	        StringEntity entity = new StringEntity(URLEncodedUtils.format(pairList2, "UTF-8"));
	        httpPost.setEntity(entity);
        }
        HttpResponse response = demo.execute(httpPost);
        String responseString = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 如果回傳是 200 OK 的話才輸出
            System.out.println(responseString);
        } else {
            System.out.println(response.getStatusLine());
        }
		return responseString;
    }
    
    public static String Delete(String url) throws IOException{
    	
    	DefaultHttpClient demo = new DefaultHttpClient();
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        
        //ArrayList<NameValuePair> pairList = new ArrayList<NameValuePair>();
        //pairList.add(new BasicNameValuePair("searchtype", "t"));
        //pairList.add(new BasicNameValuePair("searchscope", "keyword"));
        //pairList.add(new BasicNameValuePair("searcharg", "Head First Java"));
        //pairList.add(new BasicNameValuePair("SORT", "D"));

        HttpDelete httpDelete = new HttpDelete(url);
        HttpResponse response = demo.execute(httpDelete);
        String responseString = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 如果回傳是 200 OK 的話才輸出
            System.out.println(responseString);
        } else {
            System.out.println(response.getStatusLine());
        }
		return responseString;
    }
    

    
    public static String Put(String url, ArrayList<NameValuePair> pairList) throws IOException{
    	
    	DefaultHttpClient demo = new DefaultHttpClient();
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        
        //ArrayList<NameValuePair> pairList = new ArrayList<NameValuePair>();
        //pairList.add(new BasicNameValuePair("searchtype", "t"));
        //pairList.add(new BasicNameValuePair("searchscope", "keyword"));
        //pairList.add(new BasicNameValuePair("searcharg", "Head First Java"));
        //pairList.add(new BasicNameValuePair("SORT", "D"));
        
        //HttpPost httpPost = new HttpPost("http://tulips.ntu.edu.tw:1081/search*cht/a?");
        HttpPut httpPut = new HttpPut(url);
        if(pairList != null){
	        StringEntity entity = new StringEntity(URLEncodedUtils.format(pairList, "UTF-8"));
	        httpPut.setEntity(entity);
        }else{
        	
            ArrayList<NameValuePair> pairList2 = new ArrayList<NameValuePair>();
	        StringEntity entity = new StringEntity(URLEncodedUtils.format(pairList2, "UTF-8"));
	        httpPut.setEntity(entity);
        }
        HttpResponse response = demo.execute(httpPut);
        String responseString = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 如果回傳是 200 OK 的話才輸出
            System.out.println(responseString);
        } else {
            System.out.println(response.getStatusLine());
        }
		return responseString;
    }
}