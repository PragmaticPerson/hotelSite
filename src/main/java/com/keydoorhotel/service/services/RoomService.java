package com.keydoorhotel.service.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keydoorhotel.dao.RoomRepository;
import com.keydoorhotel.service.model.Room;

@Component
public class RoomService {

    private ReservationService orderService;
    private RoomRepository roomsRepository;

    @Autowired
    public RoomService(ReservationService orderService, RoomRepository roomsRepository) {
        super();
        this.orderService = orderService;
        this.roomsRepository = roomsRepository;
    }

    public List<Room> findRoomsByDate(LocalDate start, LocalDate end) {
        List<Room> orders = orderService.findOrdersIdByDate(start, end);
        if (orders.isEmpty()) {
            return roomsRepository.findAll();
        }
        return roomsRepository.findEmptyRooms(orders);
    }
}