package com.keydoorhotel.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keydoorhotel.service.dto.UserDTO;
import com.keydoorhotel.service.services.ReservationService;
import com.keydoorhotel.service.services.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

	private UserService userService;
	private ReservationService reservationService;

	public AccountController(UserService userService, ReservationService reservationService) {
		super();
		this.userService = userService;
		this.reservationService = reservationService;
	}

	@GetMapping
	public String getCurrentUser(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			var user = userService.findByEmail(username);
			var reservations = reservationService.finaAllByUserId(user.getId());

			model.addAttribute("user", new UserDTO(user));
			model.addAttribute("reservations", reservations);

			return "account";
		}

		return "redirect:/login";
	}

	@PostMapping
	public String postCurrentUser(@ModelAttribute("user") UserDTO userDTO, Model model) {
		var user = userDTO.convertToUser();

		userService.save(user);

		return "redirect:/account";
	}
}