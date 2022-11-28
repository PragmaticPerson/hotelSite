package com.keydoorhotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.services.ReservationService;
import com.keydoorhotel.service.services.RoomService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private ReservationService reservationService;
	private RoomService roomService;

	@Autowired
	public AdminController(ReservationService reservationService, RoomService roomService) {
		super();
		this.reservationService = reservationService;
		this.roomService = roomService;
	}

	@GetMapping("/book")
	public ModelAndView getBookingHistory(ModelMap model) {
		var list = reservationService.findAll();
		model.put("reservations", list);
		return new ModelAndView("admin/book/all", model);
	}

	@GetMapping("/book/{id}")
	public ModelAndView getSpecialBookingHistory(@PathVariable("id") int id, ModelMap model) {
		var entity = reservationService.findById(id);
		model.put("reservation", entity);
		model.put("rooms", roomService.findAll());
		return new ModelAndView("admin/book/one", model);
	}

	@PostMapping("/book/{id}")
	public String postSpecialBookingHistory(@ModelAttribute("reservation") Reservation reservation, ModelMap model) {
		try {
			reservationService.save(reservation);
		} catch (RuntimeException e) {
			var view = getSpecialBookingHistory(reservation.getId(), model);
			model.addAttribute("error", e.getMessage());
			return view.getViewName();
		}
		return "redirect:/admin/book";
	}

	@PostMapping("/book")
	public String deleteBookInBookingHistory(@ModelAttribute("id") int id) {
		reservationService.delete(id);
		return "redirect:/admin/book";
	}

	@PostMapping("/book/room")
	public String deleteRoomInBookingHistory(@ModelAttribute("reservationId") int reservationId,
			@ModelAttribute("roomId") int roomId) {
		reservationService.deleteRoom(reservationId, roomId);
		return "redirect:/admin/book";
	}
}
