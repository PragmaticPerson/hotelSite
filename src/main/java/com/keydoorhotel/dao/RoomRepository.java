package com.keydoorhotel.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keydoorhotel.service.model.Room;
import com.keydoorhotel.service.model.RoomType;

public interface RoomRepository extends JpaRepository<Room, Integer> {

	@Query("SELECT DISTINCT (r.type) FROM Room r WHERE r NOT IN (:ids)")
	List<RoomType> findEmptyRoomTypes(@Param("ids") List<Room> ids);

	@Query("SELECT r FROM Room r WHERE r NOT IN (:ids) AND r.type.id = (:type) ORDER BY r.id")
	List<Room> findEmptyRoomsForRoomType(@Param("ids") List<Room> ids, @Param("type") int type);

	@Query("SELECT r FROM Room r WHERE r.type.id = (:type)")
	List<Room> findAllByRoomType(@Param("type") int type);

	@Query("SELECT DISTINCT (r.type) FROM Room r WHERE r NOT IN (:ids) AND r.type.maxPeople = (:count)")
	List<RoomType> findEmptyRoomTypesByCapacity(@Param("ids") List<Room> ids, @Param("count") int peopleCount);

	@Query("SELECT r FROM Room r ORDER BY r.id")
	List<Room> findOrderByName();

	@Query("SELECT r.type FROM Room r WHERE r.type.source = (:source)")
	Optional<RoomType> findBySource(String source);
}
