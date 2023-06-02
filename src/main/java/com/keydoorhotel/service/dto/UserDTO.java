package com.keydoorhotel.service.dto;

import com.keydoorhotel.service.model.User;

public class UserDTO {

	private int id;
	private String name;
	private String email;
	private String phone;

	public UserDTO() {
		super();
	}

	public UserDTO(int id, String name, String email, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public UserDTO(User u) {
		super();
		id = u.getId();
		name = String.format("%s %s", u.getName(), u.getSurname());
		email = u.getEmail();
		phone = u.getPhone();
	}

	public User convertToUser() {
		var user = new User();

		user.setEmail(email);
		user.setPhone(phone);
		var names = name.split(" ");
		user.setName(names[0]);
		user.setSurname(names[1]);
		user.setId(id);

		return user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + "]";
	}

}
