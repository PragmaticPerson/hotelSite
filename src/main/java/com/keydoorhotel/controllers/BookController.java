package com.keydoorhotel.controllers;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import com.keydoorhotel.service.services.ReservationService;
import com.keydoorhotel.service.services.ClientService;
import com.keydoorhotel.service.services.RoomService;

@Controller
public class BookController {

    private ReservationService reservationService;
    private ClientService clientService;
    private RoomService roomService;

    @Autowired
    public BookController(ReservationService reservationService, ClientService clientService, RoomService roomService) {
        this.reservationService = reservationService;
        this.clientService = clientService;
        this.roomService = roomService;
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
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            addOrderAttribute(model);
            return "book";
        }
        clientService.save(orderDTO);
        reservationService.save(orderDTO);
        return "redirect:/";
    }

    @GetMapping("/api/rooms/{start}/{end}")
    public String getAvaliableRoomsByDateGetRequest(@PathVariable @NotNull String start, @PathVariable @NotNull String end, Model model) {
        LocalDate startDate = DateFormatter.getDate(start);
        LocalDate endDate = DateFormatter.getDate(end);
        addOrderAttribute(model);
        model.addAttribute("roomsList", roomService.findRoomsByDate(startDate, endDate));
        return "fragments/book :: roomsList";
    }

    private void addOrderAttribute(Model model) {
        model.addAttribute("order", new OrderDTO());
    }

}
