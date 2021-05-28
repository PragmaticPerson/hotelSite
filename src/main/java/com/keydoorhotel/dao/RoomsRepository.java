package com.keydoorhotel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keydoorhotel.service.model.Rooms;

public interface RoomsRepository extends JpaRepository<Rooms, Integer> {

    @Query("SELECT r FROM Rooms r WHERE r NOT IN (:ids)")
    List<Rooms> findEmptyRooms(@Param("ids") List<Rooms> ids);
}
