package com.keydoorhotel.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.Room;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r.rooms FROM Reservation r "
            + "WHERE (:start BETWEEN r.settling AND r.eviction) OR (:end BETWEEN r.settling AND r.eviction) OR "
            + "(r.settling BETWEEN :start AND :end) OR (r.eviction BETWEEN :start AND :end)")
    public List<Room> findNotEmptyRoomByDate(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
