package com.keydoorhotel.service.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keydoorhotel.dao.ResetTokenRepository;
import com.keydoorhotel.service.model.PasswordResetToken;
import com.keydoorhotel.service.model.validation.PasswordValidation;

@Service
public class ResetTokenService {

	private ResetTokenRepository repository;

	@Autowired
	public ResetTokenService(ResetTokenRepository repository) {
		super();
		this.repository = repository;
	}

	public PasswordResetToken findByToken(String token) {
		return repository.findByToken(token);
	}

	public void deleteToken(int id) {
		repository.deleteByUserId(id);
	}

	public PasswordResetToken save(PasswordResetToken token) {
		return repository.save(token);
	}

	public PasswordValidation vaidateToken(String token) {
		PasswordResetToken resetToken = repository.findByToken(token);
		if (resetToken == null) {
			return PasswordValidation.NOT_FOUND;
		} else if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
			return PasswordValidation.EXPIRED;
		}
		return PasswordValidation.CORRECT;
	}
}
