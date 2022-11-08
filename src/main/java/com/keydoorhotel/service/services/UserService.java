package com.keydoorhotel.service.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.keydoorhotel.dao.UserRepository;
import com.keydoorhotel.service.model.User;
import com.keydoorhotel.service.model.Role;

@Service
public class UserService implements UserDetailsService {

	private static final String ROLE_USER = "ROLE_USER";

	private EmailServiceImpl emailService;
	private UserRepository repository;
	private BCryptPasswordEncoder encoder;

	@Autowired
	public UserService(EmailServiceImpl emailService, UserRepository repository) {
		this.emailService = emailService;
		this.repository = repository;
		encoder = new BCryptPasswordEncoder();
	}

	public User save(User user) {
		String password = CustomPasswordGenerator.generate();
		emailService.prepareToSendMessage(user.getEmail(), password);
		user.setPassword(encoder.encode(password));
		user.setRoles(Collections.singleton(new Role(2L, ROLE_USER)));
		return repository.save(user);
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
