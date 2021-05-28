package com.keydoorhotel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keydoorhotel.service.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT r FROM Room r WHERE r NOT IN (:ids)")
    List<Room> findEmptyRooms(@Param("ids") List<Room> ids);
}
