package task;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.AuthenticationFailedException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

public class checkpassword {

  public static Boolean  execute(String username,String password) /*throws Exception */{


    
    try{
        Properties props = new Properties();

        String host = "nccu.edu.tw";
       // String username = "98703005";
       // String password = "123456";
        String provider = "pop3";
    	Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore(provider);
        store.connect(host, username, password);
        store.close();
    
    	return true;
    }catch(AuthenticationFailedException e){
    	return false;
    }catch (NoSuchProviderException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
    	return false;
	}catch (javax.mail.MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
    	return false;
	}

   // System.out.println("OK");
    /*
    Folder inbox = store.getFolder("INBOX");
    if (inbox == null) {
      System.out.println("No INBOX");
      System.exit(1);
    }
    inbox.open(Folder.READ_ONLY);

    Message[] messages = inbox.getMessages();
    for (int i = 0; i < messages.length; i++) {
      System.out.println("Message " + (i + 1));
      messages[i].writeTo(System.out);
    }
    inbox.close(false);
    */
    
  }
}

           