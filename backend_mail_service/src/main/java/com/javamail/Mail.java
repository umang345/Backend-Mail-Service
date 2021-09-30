package com.javamail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {

	public Mail() {
		super();
	}

	public void sendEmail(String message, String subject, String to[], String from) 
	{	
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

		// Getting the session object
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				HashMap<String, String> authCredentials = null;
				try {
					authCredentials = getAuthDetails();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return new PasswordAuthentication(authCredentials.get("username"), authCredentials.get("password"));
			}
		});

		// Set debug to true
		session.setDebug(true);

		// Compose the message
		MimeMessage mimeMessage = new MimeMessage(session);

		try {
			// Set the sender
			mimeMessage.setFrom(from);

			// Set the recipient
			mimeMessage.setRecipients(Message.RecipientType.TO, getRecipients(to));

			// Add Subject to message
			mimeMessage.setSubject(subject);

			// Adding text to Message
			mimeMessage.setText(message);

			// Send Message using transport class
			Transport.send(mimeMessage);

			System.out.println("Sent successfully");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendEmailWithAttachment(String message, String subject, String to[], String from, String[] url_strings) throws IOException {
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

		// Getting the session object
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				HashMap<String, String> authCredentials = null;
				try {
					authCredentials = getAuthDetails();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return new PasswordAuthentication(authCredentials.get("username"), authCredentials.get("password"));
			}
		});

		// Set debug to true
		session.setDebug(true);

		// Compose the message
		MimeMessage mimeMessage = new MimeMessage(session);

		try {
			// Set the sender
			mimeMessage.setFrom(from);

			// Set the recipient
			mimeMessage.setRecipients(Message.RecipientType.TO, getRecipients(to));

			// Add Subject to message
			mimeMessage.setSubject(subject);

			File[] files = new File[url_strings.length];
			
			for(int i=0;i<files.length;i++)
			{
				 files[i] = getFile(url_strings[i]);
			}
			

			try {
				MimeMultipart mimeMultipart = new MimeMultipart();

				MimeBodyPart textMime = new MimeBodyPart();

				MimeBodyPart[] fileMimes = new MimeBodyPart[url_strings.length];

				try {
					textMime.setText(message);
					mimeMultipart.addBodyPart(textMime);
					
					for(int i=0;i<url_strings.length;i++)
					{
						 fileMimes[i] = new MimeBodyPart();
						 fileMimes[i].attachFile(files[i]);
						 mimeMultipart.addBodyPart(fileMimes[i]);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				// Set content
				mimeMessage.setContent(mimeMultipart);

				// Send Message using transport class
				Transport.send(mimeMessage);

				System.out.println("Sent successfully");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	private HashMap<String, String> getAuthDetails() throws IOException {
		Properties prop = new Properties();
		String propFileName = "auth.properties";

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}

		HashMap<String, String> authCredentials = new HashMap<String, String>();
		authCredentials.put("username", prop.getProperty("user"));
		authCredentials.put("password", prop.getProperty("pass"));
		return authCredentials;
	}
	
	private Address[] getRecipients(String rec_strings[]) throws AddressException
	{
		 Address[] addresses = new Address[rec_strings.length];
		 for(int i=0;i<rec_strings.length;i++)
		 {
			  addresses[i] = new InternetAddress(rec_strings[i]);
		 }
		 return addresses;
	}

	private File getFile(String url_string) throws IOException {
		URL url = null;
		try {
			url = new URL(url_string);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String extension = "";
		if (url.toString().contains(".")) {
			extension = url.toString().substring(url.toString().lastIndexOf("."));
		}

		UUID uuid = UUID.randomUUID();
		System.out.println("Random UUID :" + uuid.toString());

		File file = new File("tmp/" + uuid.toString() + extension);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		OutputStream os = null;
		InputStream is = null;
		try {
			URLConnection connection = url.openConnection();
			// get input stream to the file
			is = connection.getInputStream();
			// get output stream to download file
			os = new FileOutputStream(file);
			final byte[] b = new byte[2048];
			int length;
			// read from input stream and write to output stream
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close streams
			if (os != null)
				os.close();
			if (is != null)
				is.close();
		}
		
		return file;
	}

}
