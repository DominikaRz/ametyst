/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 28.12.2021 r.
 */
package jwl.jewelry;

import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail{
    public static void send(String to, String sub, String msg) 
            throws Exception{ 
        Properties props = new Properties();
     
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");				
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
     
        Session session = Session.getInstance(props,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
  	 	return new PasswordAuthentication("ametyst2022@gmail.com","zaq1@WSX"); 
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ametyst2022@gmail.com"));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to)); //"doma4280dmr@gmail.com"
            message.setSubject(sub);
            
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
           
            Transport.send(message);
            System.out.println("Email send...");
        }
        catch(MessagingException e) { throw new Exception(e);}
    }  
}