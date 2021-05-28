//package com.keydoorhotel.controllers.rest;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.keydoorhotel.dao.OrderRepository;
//import com.keydoorhotel.service.formatter.DateFormatter;
//import com.keydoorhotel.service.model.Rooms;
//
//@RestController
//@RequestMapping("/api")
//public class RoomsRestController {
//    private OrderRepository orderRepos;
//
//    @Autowired
//    public RoomsRestController(OrderRepository orderRepos) {
//        super();
//        this.orderRepos = orderRepos;
//    }
//
//    @GetMapping("/rooms/{start}/{end}")
//    public List<Rooms> getAllRooms(@PathVariable String start, @PathVariable String end) {
//        LocalDate startDate = DateFormatter.getDate(start);
//        LocalDate endDate = DateFormatter.getDate(end);
//        return orderRepos.findRoomsByDate(startDate, endDate);
//    }
//}
