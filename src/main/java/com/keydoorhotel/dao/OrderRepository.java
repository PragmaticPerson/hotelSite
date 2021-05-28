package com.keydoorhotel.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keydoorhotel.service.model.Order;
import com.keydoorhotel.service.model.Rooms;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT r FROM Rooms r WHERE r NOT IN (SELECT o.rooms FROM Order o "
            + "WHERE :start BETWEEN o.settling AND o.eviction OR :end BETWEEN o.settling AND o.eviction)")
    public List<Rooms> findRoomsByDate(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
