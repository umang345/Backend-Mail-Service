package com.javamail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail 
{
	
	public Mail() 
	{
		super();
	}

	public void sendEmail(String message, String subject, String to, String from) {
		// Variable for host
		String host = "smtp.gmail.com";

		// Get the system properties
		Properties properties = System.getProperties();

		// Setting important information to properties object
		// Set HOST
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		//Getting the session object
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				HashMap<String,String> authCredentials = null;
				try
				{
					 authCredentials = getAuthDetails();
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				return new PasswordAuthentication(authCredentials.get("username")
													, authCredentials.get("password"));
			}
		});
		
		//Set debug to true
		session.setDebug(true);
		
		//Compose the message
		MimeMessage mimeMessage = new MimeMessage(session);
		
		try {
			//Set the sender
			mimeMessage.setFrom(from);
			
			//Set the recipient
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//Add Subject to message
			mimeMessage.setSubject(subject);
			
			//Adding text to Message
			mimeMessage.setText(message);
			
			//Send Message using transport class
			Transport.send(mimeMessage);
			
			System.out.println("Sent successfully");
		} 
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
	}
	
	private HashMap<String,String> getAuthDetails() throws IOException
	{
		Properties prop = new Properties();
		String propFileName = "auth.properties";

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		
		HashMap<String, String> authCredentials = new HashMap<String, String>();
		authCredentials.put("username",prop.getProperty("user"));
		authCredentials.put("password",prop.getProperty("pass"));
		return authCredentials;
	}

}























