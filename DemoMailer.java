package com.WebApp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.seleniumhq.jetty7.server.session.JDBCSessionManager.Session;



 
public class DemoMailer {
public static void main( String... aArguments ) throws IOException{
Emailer emailer = new Emailer();
 
    
 
      Calendar c = Calendar.getInstance();
 
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy kk:mm");
 
     
 
        String from = "tester@qa-testlab.com";
 
   
 
        String to1 = "tester2@qa-testlab.com";
 
   
 
        String to2 = "developer@qa-testlab.com";

 
        String to3 = "pm@qa-testlab.com";
 

 
        String project = "Book Shop";
 
 
        String path1 = "C:\\B2B_Pack\\test-output\\emailable-report.html";
 

 
        String path2 = "C:\\B2B_Pack\\test-output\\screenshots";
 
    emailer.sendEmail(
 
      from, to1, to2, to3,
 
       "Test Results for " + project + " " + sdf.format(c.getTime()), path1, path2);
 
   }
 
public void sendEmail(
 
    String aFromEmailAddr, String aToEmailAddr, String aToEmailAddr2, String aToEmailAddr3,
 
    String aSubject, String attach, String screenpath
 
  ) throws IOException{
 
Session session = Session.getDefaultInstance( fMailServerConfig, null );
 
    MimeMessage message = new MimeMessage( session );
 
try {
 
 
 
      message.addRecipient(
 
        Message.RecipientType.TO, new InternetAddress(aToEmailAddr)
 
      );
 

 
      message.addRecipient(
 
      Message.RecipientType.TO, new InternetAddress(aToEmailAddr2)
 
      );
 

 
      message.addRecipient(
 
      Message.RecipientType.TO, new InternetAddress(aToEmailAddr3)
 
      );
 
    
message.setSubject( aSubject );
 

message.setFrom(new InternetAddress(aFromEmailAddr));
MimeBodyPart attachFilePart = new MimeBodyPart();
FileDataSource fds = new FileDataSource(attach);
attachFilePart.setDataHandler(new DataHandler(fds));
attachFilePart.setFileName(fds.getName());
MimeBodyPart textPart = new MimeBodyPart();
textPart.setContent(attachFilePart, "text/html");
Multipart mp = new MimeMultipart();
mp.addBodyPart(attachFilePart);

File dir = new File(screenpath);
String[] children = dir.list();
if (children == null) {
System.out.println("dir does not exist");
} else {
for (int i=0; i
String filename = children[i];
System.out.println("Adding: " + filename);
attachFilePart = new MimeBodyPart();
fds = new FileDataSource(screenpath+"\\"+filename);
attachFilePart.setDataHandler(new DataHandler(fds));
attachFilePart.setFileName(fds.getName());
mp.addBodyPart(attachFilePart);
}

message.setContent(mp);
Transport.send( message );
 
      System.out.println("Mail was sent to " + aToEmailAddr + ", " + aToEmailAddr2 + ", " + aToEmailAddr3);
}
catch (ex){
System.err.println("Cannot send email. " + ex);
}
}
 
public static void refreshConfig() {
fMailServerConfig.clear();
fetchConfig();
}
 
// PRIVATE //
private static Properties fMailServerConfig = new Properties();
static {
fetchConfig();
}
 
  private static void fetchConfig() {
InputStream input = null;
try {

input = new FileInputStream("C:\\B2B_Pack\\mail.txt" );
fMailServerConfig.load( input );
}
 
    catch ( IOException ex ){
System.err.println("Cannot open and load mail server properties file.");
}
 
    finally {
try {
if ( input != null ) input.close();
}
catch ( IOException ex ){
System.err.println( "Cannot close mail server properties file." );
}
}
}
}
