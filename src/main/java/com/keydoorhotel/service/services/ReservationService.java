package com.keydoorhotel.service.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keydoorhotel.dao.ReservationRepository;
import com.keydoorhotel.dao.RoomRepository;
import com.keydoorhotel.exceptions.IllegalOperationException;
import com.keydoorhotel.service.dto.OrderDTO;
import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.Room;

@Service
public class ReservationService {

	private ReservationRepository repository;
	private RoomRepository roomRepository;

	@Autowired
	public ReservationService(ReservationRepository repository, RoomRepository roomRepository) {
		super();
		this.repository = repository;
		this.roomRepository = roomRepository;
	}

	public Reservation save(Reservation reservation) {
		var list = repository.findNotEmptyRoomByDateAndReservationId(reservation.getSettling(),
				reservation.getEviction(), reservation.getId());

		if (reservation.getRooms().removeAll(list)) {
			throw new IllegalOperationException("Rooms can't be ordered. It's one or more rooms already booked");
		}
		return repository.save(reservation);
	}

	public Reservation save(OrderDTO order) {
		var reservation = order.getReservation();
		List<Room> resultRooms = new ArrayList<>();

		List<Room> emptyRooms = new ArrayList<>();
		var notEmpty = repository.findNotEmptyRoomByDateAndReservationId(reservation.getSettling(),
				reservation.getEviction(), reservation.getId());

		for (Entry<String, Integer> entry : getRoomsCountMap(order.getRoomTypes()).entrySet()) {
			if (notEmpty.isEmpty()) {
				emptyRooms = roomRepository.findAllByRoomType(Integer.parseInt(entry.getKey()));
			} else {
				emptyRooms = roomRepository.findEmptyRoomsForRoomType(notEmpty, Integer.parseInt(entry.getKey()));
			}

			if (emptyRooms.isEmpty() || emptyRooms.size() < entry.getValue()) {
				throw new IllegalOperationException("Rooms can't be ordered. It's one or more rooms already booked");
			}

			resultRooms.addAll(emptyRooms.stream().limit(entry.getValue())
					.peek(r -> reservation.addTotalPrice(r.getType().getPrice() * order.getDurationInDates()))
					.collect(Collectors.toList()));
		}

		reservation.setRooms(resultRooms);
		return save(reservation);
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

	public List<Reservation> findAllByDateRange(LocalDate start, LocalDate end) {
		return repository.findAllByDateRange(start, end);
	}

	public List<Room> findNotEmptyRoomsByDate(LocalDate start, LocalDate end) {
		return repository.findNotEmptyRoomByDateAndReservationId(start, end, -1);
	}

	public Reservation findById(int id) {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such row with id " + id));
	}

	public List<Reservation> findAll() {
		return repository.findAll().stream().peek(r -> r.setDateDiff(r.getDurationInDates()))
				.collect(Collectors.toList());
	}

	private Map<String, Integer> getRoomsCountMap(String[] rooms) {
		Map<String, Integer> typeCount = new HashMap<String, Integer>();
		for (String type : rooms) {
			if (typeCount.containsKey(type)) {
				typeCount.put(type, typeCount.get(type) + 1);
			} else {
				typeCount.put(type, 1);
			}
		}
		return typeCount;
	}
}
