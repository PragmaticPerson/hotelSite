package com.keydoorhotel.service.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keydoorhotel.dao.RoomsRepository;
import com.keydoorhotel.service.model.Rooms;

@Component
public class RoomsService {

    private OrderService orderService;
    private RoomsRepository roomsRepository;

    @Autowired
    public RoomsService(OrderService orderService, RoomsRepository roomsRepository) {
        super();
        this.orderService = orderService;
        this.roomsRepository = roomsRepository;
    }

    public List<Rooms> findRoomsByDate(LocalDate start, LocalDate end) {
        List<Rooms> orders = orderService.findOrdersIdByDate(start, end);
        if (orders.isEmpty()) {
            return roomsRepository.findAll();
        }
        return roomsRepository.findEmptyRooms(orders);
    }
}