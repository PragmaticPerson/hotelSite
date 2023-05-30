package com.keydoorhotel.controllers;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

import javax.mail.MessagingException;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.keydoorhotel.service.dto.OrderDTO;
import com.keydoorhotel.service.formatter.DateFormatter;
import com.keydoorhotel.service.services.ReservationService;
import com.keydoorhotel.service.services.UserService;
import com.keydoorhotel.service.services.RoomService;

@Controller
public class BookController {

	private ReservationService reservationService;
	private UserService userService;
	private RoomService roomService;

	@Autowired
	public BookController(ReservationService reservationService, UserService userService, RoomService roomService) {
		this.reservationService = reservationService;
		this.userService = userService;
		this.roomService = roomService;
	}

	@GetMapping("/book")
	public String getBookPageGetRequest(Model model) {
		addOrderAttribute(model);
		return "book";
	}

	@GetMapping("/api/rooms/{start}/{end}")
	public String getAvaliableRoomsByDateGetRequest(@PathVariable @NotNull String start,
			@PathVariable @NotNull String end, @RequestParam int adult, @RequestParam int child, Model model) {
		LocalDate startDate = DateFormatter.getDate(start);
		LocalDate endDate = DateFormatter.getDate(end);
		var diff = DAYS.between(startDate, endDate);
		addOrderAttribute(model);

		var listOfTypes = roomService.findEmptyRoomTypes(startDate, endDate, adult, child);
		model.addAttribute("roomsList", listOfTypes);
		model.addAttribute("days", diff);
		return "fragments/book :: roomsList";
	}

	@PostMapping("/book")
	public String fromBookPagePostRequest(@ModelAttribute("order") @Valid OrderDTO orderDTO, BindingResult result,
			Model model) throws MessagingException {
		if (result.hasErrors()) {
			model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
			addOrderAttribute(model);
			return "book";
		}
		userService.createUser(orderDTO.getUser());
		reservationService.save(orderDTO);
		return "redirect:/";
	}

	private void addOrderAttribute(Model model) {
		model.addAttribute("order", new OrderDTO());
	}

}
