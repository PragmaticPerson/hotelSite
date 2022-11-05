package com.keydoorhotel.service.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.keydoorhotel.dao.ClientRepository;
import com.keydoorhotel.service.model.Client;
import com.keydoorhotel.service.model.Role;

@Service
public class ClientService implements UserDetailsService {

	private ClientRepository repository;
	private BCryptPasswordEncoder encoder;

	@Autowired
	public ClientService(ClientRepository repository) {
		this.repository = repository;
		encoder = new BCryptPasswordEncoder();
	}

	public Client save(Client client) {
		client.setPassword(encoder.encode(CustomPasswordGenerator.generate()));
		client.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
		return repository.save(client);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Client client = repository.findByEmail(email);
		if (client == null) {
			throw new UsernameNotFoundException("No user found with email: " + email);
		}
		return client;
	}
}
