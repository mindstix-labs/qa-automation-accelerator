/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Class used for
 * <li>Configuring mail</li>
 * <li>Sending mail</li>
 * Before using MailUtility class update your email address and password in emailconfig.properties.
 * You may also need to grant permission for sending email through 
 * less secure Application from your email account
 * @author Mindstix
 */
public final class MailUtility {

	private final static Logger LOGGER = LoggerFactory.getLogger(MailUtility.class);

	private static Properties mailProperties;
	private static Properties emailConfigs;
	private static Session session;
	private static Message message;

	static {
		emailConfigs = PropertiesUtility.getEmailconfigs();
		mailProperties = new Properties();
		configureMailDetails();
	}

	/**
	 * Private constructor
	 */
	private MailUtility() {

	}

	private static void configureMailDetails() {
		if(emailConfigs.getProperty("mailAddress").equals("YOUR_EMAIL_ADDRESS")) {
			throw new RuntimeException("emailconfig not initialized with credentials");
		}
		mailProperties.put("mail.smtp.auth", "true");
		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailProperties.put("mail.smtp.host", emailConfigs.getProperty("emailHost"));
		mailProperties.put("mail.smtp.port", "25");

		session = Session.getInstance(mailProperties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailConfigs.getProperty("mailAddress"), emailConfigs.getProperty("mailPassword"));
			}
		});
		LOGGER.info("Email properties have been configured successfully");
	}

	/**
	 * sendMail takes three parameters and sends mail with given recipients
	 * email address, subject and body.
	 * 
	 * @param to
	 * @param subject
	 * @param mailBody
	 */
	public static void sendMail(String to, String subject, String mailBody) {
		try {
			LOGGER.info("Sending Email to {}!", to);
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailConfigs.getProperty("mailAddress")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(mailBody);
			message.setContent(mailBody, "text/html; charset=utf-8");
			Transport.send(message);
			LOGGER.info("Email has been successfully sent to {}!", to);
		} catch (MessagingException e) {
			LOGGER.error("Failed to sent mail to {} !", to, e);
		}
	}
}
