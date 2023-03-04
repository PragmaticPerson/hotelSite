package com.keydoorhotel.service.services;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keydoorhotel.dao.RoomRepository;
import com.keydoorhotel.service.model.Room;

@Component
public class RoomService {

	private ReservationService reservationService;
	private RoomRepository roomRepository;

	@Autowired
	public RoomService(ReservationService reservationService, RoomRepository roomRepository) {
		this.reservationService = reservationService;
		this.roomRepository = roomRepository;
	}

	public Room save(Room room) {
		return roomRepository.saveAndFlush(room);
	}

	public Room findById(int id) {
		return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such row with id " + id));
	}

	public void delete(int id) {
		roomRepository.deleteById(id);
	}

	public List<Room> findAll() {
		return roomRepository.findAllOrderById();
	}

	public List<Room> findRoomsByDate(LocalDate start, LocalDate end) {
		List<Room> orderedRooms = reservationService.findNotEmptyRoomByDate(start, end);
		if (orderedRooms.isEmpty()) {
			return roomRepository.findAll();
		}
		return roomRepository.findEmptyRooms(orderedRooms);
	}
}