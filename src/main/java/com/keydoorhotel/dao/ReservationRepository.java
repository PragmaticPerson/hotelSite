package com.keydoorhotel.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.Room;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	@Query("SELECT r.rooms FROM Reservation r WHERE r.id <> :id AND ("
			+ "(:start BETWEEN r.settling AND r.eviction) OR (:end BETWEEN r.settling AND r.eviction) OR "
			+ "(r.settling BETWEEN :start AND :end) OR (r.eviction BETWEEN :start AND :end) )")
	public List<Room> findNotEmptyRoomByDateAndReservationId(@Param("start") LocalDate start,
			@Param("end") LocalDate end, @Param("id") int id);

	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM room_reservation WHERE reservation_id = :reservationId AND "
			+ "room_id = :roomId")
	public void deleteRoom(int reservationId, int roomId);
}
