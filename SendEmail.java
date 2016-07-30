package com.WebApp;

import java.util.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.openqa.selenium.remote.server.Session;

import com.opera.core.systems.scope.protos.UmsProtos.TransportMessage;

public class SendEmail
{
   public static void main(String [] args)
   {    
      // Recipient's email ID needs to be mentioned.
      String to = "someemail.com";

      // Sender's email ID needs to be mentioned
      String from = "otheremail.com";

      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      javax.mail.Session session = Session.getDefaultInstance(properties);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("This is the Subject Line!");

         // Now set the actual message
         message.setText("This is actual message");

         // Send message
         TransportMessage.send(message);
         System.out.println("Sent message successfully....");
      }catch (MultiException mex) {
         mex.printStackTrace();
      }
   }
}
