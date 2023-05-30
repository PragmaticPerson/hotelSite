package com.keydoorhotel.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keydoorhotel.service.services.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

	private UserService userService;

	public AccountController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping
	public String getCurrentUser(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			var user = userService.loadUserByUsername(username);

			model.addAttribute("user", user);

			return "account";
		}

		return "redirect:/login";
	}
}
