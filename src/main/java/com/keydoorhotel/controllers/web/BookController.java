package com.keydoorhotel.controllers.web;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.keydoorhotel.service.dto.OrderDTO;
import com.keydoorhotel.service.formatter.DateFormatter;
import com.keydoorhotel.service.services.OrderService;
import com.keydoorhotel.service.services.PeopleService;
import com.keydoorhotel.service.services.RoomsService;

@Controller
public class BookController {

    private OrderService orderService;
    private PeopleService peopleService;
    private RoomsService roomsService;

    @Autowired
    public BookController(OrderService orderService, PeopleService peopleService, RoomsService roomsService) {
        this.orderService = orderService;
        this.peopleService = peopleService;
        this.roomsService = roomsService;
    }

    @GetMapping("/book")
    public String getBookPageGetRequest(Model model) {
        addOrderAttribute(model);
        return "book";
    }

    @PostMapping("/book")
    public String fromBookPagePostRequest(@ModelAttribute("order") @Valid OrderDTO orderDTO, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors().get(0));
            addOrderAttribute(model);
            return "book";
        }
        peopleService.save(orderDTO);
        orderService.save(orderDTO);
        return "redirect:/";
    }

    @GetMapping("/api/rooms/{start}/{end}")
    public String getAvaliableRoomsByDateGetRequest(@PathVariable String start, @PathVariable String end, Model model) {
        LocalDate startDate = DateFormatter.getDate(start);
        LocalDate endDate = DateFormatter.getDate(end);
        addOrderAttribute(model);
        model.addAttribute("roomsList", roomsService.findRoomsByDate(startDate, endDate));
        return "fragments/book :: roomsList";
    }

    private void addOrderAttribute(Model model) {
        model.addAttribute("order", new OrderDTO());
    }

}
