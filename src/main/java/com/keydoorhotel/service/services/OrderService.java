package com.keydoorhotel.service.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keydoorhotel.dao.OrderRepository;
import com.keydoorhotel.service.dto.OrderDTO;
import com.keydoorhotel.service.model.Order;
import com.keydoorhotel.service.model.Rooms;

@Component
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        super();
        this.orderRepository = orderRepository;
    }

    public Order save(OrderDTO orderDTO) {
        return orderRepository.save(orderDTO.getOrder());
    }

    public List<Rooms> findRooms(LocalDate start, LocalDate end) {
        return orderRepository.findRoomsByDate(start, end);
    }
}
