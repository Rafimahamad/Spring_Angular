package com.project.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	
	Logger log=LoggerFactory.getLogger(EmailServiceImpl.class);

	
	
	public boolean sendEmail(String subject,String message,String to) {
		
		
		
		
		String from ="dsrsvforum@gmail.com";
		
//		Variable for mail
		
		String host="smtp.gmail.com";
		
		//get the system properties
		Properties properties = System.getProperties();
		log.info("properties : {}",properties);
		
		// setting important information to properties object
		
		//host get 
		
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
//		step 1: to get session object
		
		Session session=Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				
				return new PasswordAuthentication(from, "dsrs@4123");
			}
		
		});
		
		session.setDebug(true);
		//step 2: compose the message(text,multi media)
		
		
		MimeMessage m = new MimeMessage(session);
		
		try {
			
			//from email
			m.setFrom(from);
		
			
		// adding recipeint address
		m.addRecipient(Message.RecipientType.TO, new  InternetAddress(to));
		
		//adding subject to message
		
		m.setSubject(subject);
		
		
//		adding text to message
		
	
		
		m.setContent(message,"text/html");
		
//		send
//		step3: send the message using Transport class
		
		Transport.send(m);
		log.info("sent Success....");
		
		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return true;

	}
}
