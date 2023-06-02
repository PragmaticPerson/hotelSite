package com.keydoorhotel.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.keydoorhotel.service.dto.MobiscrollResourceDTO;
import com.keydoorhotel.service.dto.MobiscrollTimelineDTO;
import com.keydoorhotel.service.dto.RoomDTO;
import com.keydoorhotel.service.dto.UserDTO;
import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.Room;
import com.keydoorhotel.service.model.RoomType;
import com.keydoorhotel.service.services.ImageService;
import com.keydoorhotel.service.services.ReservationService;
import com.keydoorhotel.service.services.RoomService;
import com.keydoorhotel.service.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private ReservationService reservationService;
	private RoomService roomService;
	private ImageService imageService;
	private UserService userService;

	@Autowired
	public AdminController(ReservationService reservationService, RoomService roomService, ImageService imageService,
			UserService userService) {
		super();
		this.reservationService = reservationService;
		this.roomService = roomService;
		this.imageService = imageService;
		this.userService = userService;
	}

	@GetMapping("/api/calendar/{start}/{end}")
	@ResponseBody
	public List<MobiscrollTimelineDTO> getAllReservationsForPeriodForCalendar(@PathVariable("start") String startStr,
			@PathVariable("end") String endStr, Model model) throws ParseException {

		List<MobiscrollTimelineDTO> result = new ArrayList<>();

		LocalDate start = LocalDate.parse(startStr.split("T")[0]);
		LocalDate end = LocalDate.parse(endStr.split("T")[0]);

		var allReservations = reservationService.findAllByDateRange(start, end);
		allReservations.stream().forEach(r -> result.addAll(r.convertToMobiscrollTimelineDTOs()));

		return result;
	}

	@GetMapping("/api/calendar/rooms")
	@ResponseBody
	public List<MobiscrollResourceDTO> getAllRoomsForCalendar(Model model) throws ParseException {

		List<MobiscrollResourceDTO> result = new ArrayList<>();

		roomService.findAll().forEach(r -> result.add(new MobiscrollResourceDTO(r)));

		return result;
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

	@PostMapping("/rooms/{id}/room")
	public String postSpecialBookingHistory(@PathVariable("id") int id, @RequestParam("roomName") String name,
			ModelMap model) {
		var type = new RoomType();
		type.setId(id);
		var room = new Room();
		room.setName(name);
		room.setType(type);

		System.out.println(room);

		roomService.save(room);

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
		var list = roomService.findAllRoomTypes();
		List<RoomDTO> rooms = new ArrayList<>();
		for (RoomType r : list) {
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
		model.addAttribute("room", new RoomType());
		return new ModelAndView("/admin/rooms/new", model);
	}

	@PostMapping("/rooms/new")
	public String saveNewRoom(@ModelAttribute("room") RoomType room) {
		roomService.save(room);
		return "redirect:/admin/rooms";
	}

	@GetMapping("/rooms/{id}")
	public ModelAndView getSpecialRoom(@PathVariable("id") int id, ModelMap model) throws IOException {
		var roomEntity = roomService.findTypeById(id);
		var rooms = roomService.findAllByRoomType(roomEntity.getId());
		var imageNames = imageService.getAllImageNames(roomEntity.getSource());

		model.put("images", imageNames);
		model.put("roomType", new RoomDTO(roomEntity));
		model.put("rooms", rooms);

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

	@GetMapping("/client")
	public String getAllClients(Model model) {
		var users = userService.findAll().stream().map(UserDTO::new).collect(Collectors.toList());

		model.addAttribute("users", users);

		return "/admin/client";
	}

	@PostMapping("/client/{id}")
	@ResponseBody
	public ResponseEntity<String> getAllClients(@ModelAttribute UserDTO userDTO, Model model) {
		try {
			userService.saveUnsecuredFields(userDTO.convertToUser());
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.ok("Успешный запрос");
	}
}
