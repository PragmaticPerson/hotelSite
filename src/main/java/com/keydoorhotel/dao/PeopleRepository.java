package com.keydoorhotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keydoorhotel.service.model.People;

public interface PeopleRepository extends JpaRepository<People, Integer> {

}
