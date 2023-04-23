package com.keydoorhotel.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keydoorhotel.service.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

	@Query("SELECT r FROM Room r WHERE r NOT IN (:ids)")
	List<Room> findEmptyRooms(@Param("ids") List<Room> ids);

	@Query("SELECT r FROM Room r ORDER BY r.id")
	List<Room> findAllOrderById();

	Optional<Room> findBySource(String source);
}
