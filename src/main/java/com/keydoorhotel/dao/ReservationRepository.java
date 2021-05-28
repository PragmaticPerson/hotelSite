package com.keydoorhotel.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.Room;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r.rooms FROM Reservation r WHERE (:start BETWEEN r.settling AND r.eviction) OR (:end BETWEEN r.settling AND r.eviction)")
    public List<Room> findOrdersIdByDate(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
