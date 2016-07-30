package com.WebApp;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import org.testng.annotations.Test;

import java.util.*;

/*
 * READ ME
Two Jars are needed to send email 
1.javamail-1.4.7.jar
2.activation-1.1.1.jar


  To use this program, change values for the following three constants,

    SMTP_HOST_NAME -- Has your SMTP Host Name
    SMTP_AUTH_USER -- Has your SMTP Authentication UserName
    SMTP_AUTH_PWD  -- Has your SMTP Authentication Password

  Next change values for fields

  emailMsgTxt  -- Message Text for the Email
  emailSubjectTxt  -- Subject for email
  emailFromAddress -- Email Address whose name will appears as "from" address

  Next change value for "emailList".
  This String array has List of all Email Addresses to Email Email needs to be sent to.


  Next to run the program, execute it as follows,

  SendMailUsingAuthentication authProg = new SendMailUsingAuthentication();

*/

public class SendMailUsingAuthentication
{





  private static final String SMTP_HOST_NAME = "mail.twc.com";
  private static final String SMTP_AUTH_USER = "myemail@email.com";
  private static final String SMTP_AUTH_PWD  = "password";

  
  private static final String emailSubjectTxt  = "Automation Testing Report on  " + new Date() + " ";
  private static final String emailMsgTxt      = "Ben send you this report";
  
  private static final String emailFromAddress = "myemail@email.com";

  // Add List of Email address to who email needs to be sent to
  private static final String[] emailList = {"myemail@email.com", "myemail@email_1.com" "};

 @Test
  public static void maineeee() throws Exception
  {
    SendMailUsingAuthentication smtpMailSender = new SendMailUsingAuthentication();
    smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
    System.out.println("Sucessfully Sent mail to All Users");
  }


private BodyPart messageBodyPart;

  public void postMail( String recipients[ ], String subject,
                            String message , String from) throws MessagingException
  {
    boolean debug = false;

     //Set the host smtp address
     Properties props = new Properties();
     props.put("mail.smtp.host", SMTP_HOST_NAME);
     props.put("mail.smtp.auth", "true");

    Authenticator auth = new SMTPAuthenticator();
    Session session = Session.getDefaultInstance(props, auth);

    session.setDebug(debug);

    // create a message
    Message msg = new MimeMessage(session);

    // set the from and to address
    InternetAddress addressFrom = new InternetAddress(from);
    msg.setFrom(addressFrom);
  
    // new code added
  Multipart multipart = new MimeMultipart();
  //multipart.addBodyPart(messageBodyPart);

    // Part two is attachment
    messageBodyPart = new MimeBodyPart();
  
    String filename = "C:\\location";
  
    DataSource source = new FileDataSource(filename);
    messageBodyPart.setDataHandler(new DataHandler(source));
    
    messageBodyPart.setFileName("Attachment Here:");
    messageBodyPart.setDescription(message);
    multipart.addBodyPart(messageBodyPart);

    // Put parts in message
   msg.setContent(multipart);

    InternetAddress[] addressTo = new InternetAddress[recipients.length];
    for (int i = 0; i < recipients.length; i++)
    {
        addressTo[i] = new InternetAddress(recipients[i]);
    }
    msg.setRecipients(Message.RecipientType.TO, addressTo); 

  // Setting the Subject and Content Type
msg.setSubject(subject);
    msg.setContent(multipart);
      Transport.send(msg);
 }

/**
* SimpleAuthenticator is used to do simple authentication
* when the SMTP server requires it.
*/
private class SMTPAuthenticator extends javax.mail.Authenticator
{

    public PasswordAuthentication getPasswordAuthentication()
    {
        String username = SMTP_AUTH_USER;
        String password = SMTP_AUTH_PWD;
        return new PasswordAuthentication(username, password);
    }
}

}
