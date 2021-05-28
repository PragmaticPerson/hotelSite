package com.keydoorhotel.service.services;

import java.time.LocalDate;
import java.util.List;

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

    public List<Room> findRoomsByDate(LocalDate start, LocalDate end) {
        List<Room> orderedRooms = reservationService.findOrdersIdByDate(start, end);
        if (orderedRooms.isEmpty()) {
            return roomRepository.findAll();
        }
        return roomRepository.findEmptyRooms(orderedRooms);
    }
}