package com.keydoorhotel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.keydoorhotel.service.model.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

	List<RoomType> findByMaxPeopleOrderById(int maxPeople);
}
