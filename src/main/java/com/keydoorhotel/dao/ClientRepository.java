package com.keydoorhotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keydoorhotel.service.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

	Client findByEmail(String email);
}
