package com.keydoorhotel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.keydoorhotel.service.services.RoomService;

@Controller
public class RoomSenderController {

	private RoomService roomService;

	public RoomSenderController(RoomService roomService) {
		super();
		this.roomService = roomService;
	}

	@GetMapping("/rooms")
	public String getRoomsPage(Model model) {
		var list = roomService.findAll();
		model.addAttribute("rooms", list);
		return "rooms";
	}

	@GetMapping("/rooms/{source}")
	public String getCurrentRoomPage(@PathVariable String source, Model model) {
		var room = roomService.findBySource(source);
		model.addAttribute("room", room);
		return "dynamicRoom";
	}
}
