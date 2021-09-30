package com.javamail;


public class App {
	public static void main(String[] args) 
	{
		
		//Sending a simple message 
		System.out.println("____Preparing to send message____");
		String message = "Hello Dear, this is a test mail";
		String subject = "CodersArea : Security check";
		String to = "umangagarwal345@gmail.com";
		String from = "azureforazure@gmail.com";
		
		Mail mail = new Mail();
		mail.sendEmail(message, subject, to, from);
	}

	
}


















