package com.keydoorhotel.service.services;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@PropertySource("classpath:application.properties")
public class EmailService {

	@Value("${spring.mail.username}")
	private String from;
	private JavaMailSender emailSender;
	private SpringTemplateEngine thymeleafTemplateEngine;

	@Autowired
	public EmailService(JavaMailSender emailSender, SpringTemplateEngine thymeleafTemplateEngine) {
		this.emailSender = emailSender;
		this.thymeleafTemplateEngine = thymeleafTemplateEngine;
	}

	public void sendMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(String.format("%s@yandex.ru", from));
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void sendHtmlMessage(String to, String subject, String htmlTemplate) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		message.setFrom(String.format("%s@yandex.ru", from));
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlTemplate, true);
		emailSender.send(message);
	}

	public void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel)
			throws MessagingException {

		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);
		String htmlBody = thymeleafTemplateEngine.process("mail-templates/template-thymeleaf.html", thymeleafContext);

		sendHtmlMessage(to, subject, htmlBody);
	}

	public void prepareToSendMessage(String to, String password, String token) {
		String subject = "Регистрация на keyAndDoor";
		String text = "Вы зарегистрировались на сайте keyAndDoor.ru. Для доступа к аккаунту воспользуйтесь "
				+ "вашей почтой и паролем " + password + ".";
		sendMessage(to, subject, text);
	}
}
