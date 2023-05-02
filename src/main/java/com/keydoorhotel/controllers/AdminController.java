package com.keydoorhotel.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.keydoorhotel.service.dto.RoomDTO;
import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.Room;
import com.keydoorhotel.service.services.ImageService;
import com.keydoorhotel.service.services.ReservationService;
import com.keydoorhotel.service.services.RoomService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private ReservationService reservationService;
	private RoomService roomService;
	private ImageService imageService;

	@Autowired
	public AdminController(ReservationService reservationService, RoomService roomService, ImageService imageService) {
		super();
		this.reservationService = reservationService;
		this.roomService = roomService;
		this.imageService = imageService;
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

	@GetMapping("/rooms")
	public ModelAndView getRooms(ModelMap model) {
		var list = roomService.findAll();
		List<RoomDTO> rooms = new ArrayList<>();
		for (Room r : list) {
			rooms.add(new RoomDTO(r));
		}
		model.put("rooms", rooms);
		return new ModelAndView("admin/rooms/all", model);
	}

	@PostMapping("/rooms")
	public String deleteRoomInAllRooms(@ModelAttribute("id") int id) {
		roomService.delete(id);
		return "redirect:/admin/rooms";
	}

	@GetMapping("/rooms/new")
	public ModelAndView openViewToAddRoom(ModelMap model) {
		model.addAttribute("room", new Room());
		return new ModelAndView("/admin/rooms/new", model);
	}

	@PostMapping("/rooms/new")
	public String saveNewRoom(@ModelAttribute("room") Room room) {
		roomService.save(room);
		return "redirect:/admin/rooms";
	}

	@GetMapping("/rooms/{id}")
	public ModelAndView getSpecialRoom(@PathVariable("id") int id, ModelMap model) throws IOException {
		var roomEntity = roomService.findById(id);
		var imageNames = imageService.getAllImageNames(roomEntity.getSource());

		model.put("images", imageNames);
		model.put("room", new RoomDTO(roomEntity));
		return new ModelAndView("admin/rooms/one", model);
	}

	@PostMapping("/rooms/{id}")
	public String postSpecialRoom(@ModelAttribute("room") Room room, ModelMap model) throws IOException {
		try {
			roomService.save(room);
		} catch (RuntimeException e) {
			var view = getSpecialRoom(room.getId(), model);
			model.addAttribute("error", e.getMessage());
			return view.getViewName();
		}
		return "redirect:/admin/rooms";
	}

	@PostMapping("/rooms/{id}/images")
	public String saveNewImageToRoom(@PathVariable int id, @RequestParam MultipartFile file) {
		imageService.saveImageForRoom(id, file);
		return "redirect:/admin/rooms/" + id;
	}

	@PostMapping("/rooms/{id}/images/{imageName}")
	public String deleteImageFromRoom(@PathVariable int id, @PathVariable String imageName) {
		imageService.deleteImageFromRoom(id, imageName);
		return "redirect:/admin/rooms/" + id;
	}
}
