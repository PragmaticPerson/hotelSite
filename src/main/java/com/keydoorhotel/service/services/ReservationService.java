package com.keydoorhotel.service.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keydoorhotel.dao.ReservationRepository;
import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.Room;

@Component
public class ReservationService {

	private ReservationRepository orderRepository;

	@Autowired
	public ReservationService(ReservationRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	public Reservation save(Reservation reservation) {
		return orderRepository.save(reservation);
	}

	public List<Room> findOrdersIdByDate(LocalDate start, LocalDate end) {
		return orderRepository.findNotEmptyRoomByDate(start, end);
	}
}
