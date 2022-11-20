package com.keydoorhotel.service.model.validation;

public enum PasswordValidation {
	EXPIRED("Expired token"), NOT_FOUND("Token not found"), CORRECT("Correct");

	private String name;

	private PasswordValidation(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
