package com.keydoorhotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keydoorhotel.service.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);
}
