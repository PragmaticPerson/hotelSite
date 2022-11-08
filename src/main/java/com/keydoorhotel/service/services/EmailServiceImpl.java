package com.keydoorhotel.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class EmailServiceImpl {

	@Value("${spring.mail.username}")
	private String from;
	private JavaMailSender emailSender;

	@Autowired
	public EmailServiceImpl(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void sendMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(String.format("%s@yandex.ru", from));
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void prepareToSendMessage(String to, String password) {
		String subject = "Регистрация на keyAndDoor";
		String text = "Вы зарегистрировались на сайте keyAndDoor.ru. Для доступа к аккаунту воспользуйтесь "
				+ "вашей почтой и паролем " + password;
		sendMessage(to, subject, text);
	}
}
