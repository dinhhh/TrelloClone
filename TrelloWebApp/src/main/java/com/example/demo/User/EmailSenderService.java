package com.example.demo.User;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@Service
public class EmailSenderService {

	public EmailSenderService(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}

	private JavaMailSender javaMailSender;

	@Async
	public void sendEmail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}
}
