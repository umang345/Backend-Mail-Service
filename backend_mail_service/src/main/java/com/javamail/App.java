package com.javamail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
	public static void main(String[] args) throws IOException
	{
		
		MailUtils mu = new MailUtils();
		HashMap<String, String> mailProps = new HashMap<String, String>();
		mailProps = mu.getMailDetails();
		
		for(Map.Entry<String, String> k : mailProps.entrySet())
		System.out.println(k.getKey() + " : "+k.getValue());
		
		String message = mailProps.get("message");
		String subject = mailProps.get("subject");
		String to[] = mailProps.get("recipients").split(" , ");
		String from = mailProps.get("sender"); 
		String attachments[] = 
				(mailProps.get("attachments").length()==0)? null :mailProps.get("attachments").split(" , ");
		System.out.println("Len : "+mailProps.get("attachments").length());
		
		Mail mail = new Mail();
		
		if(attachments==null)
		{
			 mail.sendEmail(message, subject, to, from);
		}
		else
		{
			 mail.sendEmailWithAttachment(message, subject, to, from, attachments);
		}
	}
	
	

	
}


















