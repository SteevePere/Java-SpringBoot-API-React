package com.quest.etna.controllers.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailHelper
{
	
	@Autowired
    private JavaMailSender _javaMailSender;

	
	public void sendEmail(String subject, String text)
	{
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setTo("steevepere@gmail.com");
		msg.setSubject(subject);msg.setText(text);

		_javaMailSender.send(msg);
	}
}
