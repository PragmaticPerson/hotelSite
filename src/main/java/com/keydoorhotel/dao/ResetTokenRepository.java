package com.keydoorhotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.keydoorhotel.service.model.PasswordResetToken;

public interface ResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

	PasswordResetToken findByToken(String token);

	@Modifying
	void deleteByUserId(int id);
}
