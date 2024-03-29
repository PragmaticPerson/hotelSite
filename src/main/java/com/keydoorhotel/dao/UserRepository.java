package com.keydoorhotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.keydoorhotel.service.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	@Transactional
	@Modifying
	@Query("UPDATE User SET name = (:name), surname = (:surname), phone = (:phone), email = (:email) WHERE id = (:id)")
	void saveUnsecuredFields(int id, String name, String surname, String phone, String email);

	@Transactional
	@Modifying
	@Query("UPDATE User SET password = (:pass) WHERE id = (:id)")
	void updateUserPassword(int id, String pass);
}
