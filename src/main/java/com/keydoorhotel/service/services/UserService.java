package com.keydoorhotel.service.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.keydoorhotel.dao.UserRepository;
import com.keydoorhotel.service.model.User;
import com.keydoorhotel.service.model.PasswordResetToken;
import com.keydoorhotel.service.model.Role;

@Service
public class UserService implements UserDetailsService {

	private static final String ROLE_USER = "ROLE_USER";
	@Value("${application.host.name}")
	private String host;

	private EmailService emailService;
	private UserRepository repository;
	private ResetTokenService tokenService;
	private BCryptPasswordEncoder encoder;

	@Autowired
	public UserService(EmailService emailService, UserRepository repository, ResetTokenService tokenService,
			BCryptPasswordEncoder encoder) {
		this.emailService = emailService;
		this.repository = repository;
		this.tokenService = tokenService;
		this.encoder = encoder;
	}

	public User createUser(User user) throws MessagingException {
		String password = CustomPasswordGenerator.generate();
		String token = UUID.randomUUID().toString();

		user.setPassword(encoder.encode(password));
		user.setRoles(Collections.singleton(new Role(2L, ROLE_USER)));

		User newUser = save(user);
		PasswordResetToken resetToken = new PasswordResetToken(token, newUser);
		tokenService.save(resetToken);

		String url = String.format("http://%s/password/change?token=%s", host, token);

		var models = new HashMap<String, Object>();
		models.put("pass", password);
		models.put("passChangeUrl", url);

		emailService.sendMessageUsingThymeleafTemplate(user.getEmail(), "Новый пароль", models);

		return newUser;
	}

	public boolean isOldPassCorrect(User user, String oldPass) {
		return encoder.matches(oldPass, user.getPassword());
	}

	public User findById(int id) {
		return repository.getReferenceById(id);
	}

	public User save(User user) {
		return repository.save(user);
	}

	public void updatePassword(User user, String newPass) {
		repository.updateUserPassword(user.getId(), encoder.encode(newPass));
		tokenService.deleteToken(user.getId());
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User client = repository.findByEmail(email);
		if (client == null) {
			throw new UsernameNotFoundException("No user found with email: " + email);
		}
		return client;
	}
}
