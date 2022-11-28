package com.keydoorhotel.service.services;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keydoorhotel.dao.ReservationRepository;
import com.keydoorhotel.exceptions.IllegalOperationException;
import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.Room;

@Service
public class ReservationService {

	private ReservationRepository repository;

	@Autowired
	public ReservationService(ReservationRepository repository) {
		super();
		this.repository = repository;
	}

	public Reservation save(Reservation reservation) {
		var list = repository.findNotEmptyRoomByDateAndReservationId(reservation.getSettling(),
				reservation.getEviction(), reservation.getId());

		if (reservation.getRooms().removeAll(list)) {
			throw new IllegalOperationException("Rooms can't be ordered. It's one or more rooms already booked");
		}
		return repository.save(reservation);
	}

	@Transactional
	public void delete(int id) {
		repository.deleteById(id);
	}

	@Transactional
	public void deleteRoom(int reservationId, int roomId) {
		var reservation = findById(reservationId);
		if (reservation.getRooms().size() == 1) {
			delete(reservationId);
		} else {
			repository.deleteRoom(reservationId, roomId);
		}
	}

	public List<Room> findNotEmptyRoomByDate(LocalDate start, LocalDate end) {
		return repository.findNotEmptyRoomByDateAndReservationId(start, end, -1);
	}

	public Reservation findById(int id) {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such row with id " + id));
	}

	public List<Reservation> findAll() {
		return repository.findAll();
	}
}
