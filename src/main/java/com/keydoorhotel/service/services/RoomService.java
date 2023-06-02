package com.keydoorhotel.service.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keydoorhotel.dao.RoomRepository;
import com.keydoorhotel.dao.RoomTypeRepository;
import com.keydoorhotel.service.model.Room;
import com.keydoorhotel.service.model.RoomType;

@Component
public class RoomService {

	private ReservationService reservationService;
	private RoomRepository roomRepository;
	private RoomTypeRepository roomTypeRepository;

	@Autowired
	public RoomService(ReservationService reservationService, RoomRepository roomRepository,
			RoomTypeRepository roomTypeRepository) {
		super();
		this.reservationService = reservationService;
		this.roomRepository = roomRepository;
		this.roomTypeRepository = roomTypeRepository;
	}

	public Room save(Room room) {
		return roomRepository.saveAndFlush(room);
	}

	public RoomType save(RoomType room) {
		return roomTypeRepository.saveAndFlush(room);
	}

	public Room findById(int id) {
		return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such row with id " + id));
	}

	public List<Room> findAllByRoomType(int type) {
		return roomRepository.findAllByRoomType(type);
	}

	public RoomType findTypeById(int id) {
		return roomTypeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("No such row with id " + id));
	}

	public RoomType findBySource(String source) {
		return roomRepository.findBySource(source)
				.orElseThrow(() -> new EntityNotFoundException("No such row with source " + source));
	}

	public List<Room> findAll() {
		return roomRepository.findOrderByName();
	}

	public List<RoomType> findAllRoomTypes() {
		return roomTypeRepository.findAll();
	}

	public List<RoomType> findEmptyRoomTypes(LocalDate start, LocalDate end, int adult, int child) {
		List<Room> orderedRooms = reservationService.findNotEmptyRoomsByDate(start, end);

		if (adult > 2) {
			if (orderedRooms.isEmpty()) {
				return roomTypeRepository.findAll();
			}

			return roomRepository.findEmptyRoomTypes(orderedRooms).stream()
					.sorted(Comparator.comparingInt(RoomType::getId)).collect(Collectors.toList());
		} else if (adult > 0) {
			if (orderedRooms.isEmpty()) {
				return roomTypeRepository.findByMaxPeopleOrderById(adult);
			}

			return roomRepository.findEmptyRoomTypesByCapacity(orderedRooms, adult);
		}

		throw new IllegalArgumentException("Adult count less or equals zero.");
	}

	public void delete(int id) {
		roomTypeRepository.deleteById(id);
	}
}