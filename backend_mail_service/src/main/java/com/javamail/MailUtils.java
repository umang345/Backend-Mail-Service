package com.javamail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class MailUtils 
{
	public HashMap<String,String> getMailDetails() throws IOException
	{
		 HashMap<String,String> values = new HashMap<String, String>();
		 
		 Properties prop = new Properties();
			String propFileName = "mail.properties";

			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			String recipients = prop.getProperty("recipients").trim();
			String sender = prop.getProperty("sender").trim();
			String message = prop.getProperty("message").trim();
			String subject = prop.getProperty("subject").trim();
			String attachments = prop.getProperty("attachments").trim();
			values.put("recipients", recipients);
			values.put("sender", sender);
			values.put("message", message);
			values.put("subject", subject);
			values.put("attachments", attachments);
			return values;
			
	}

}
