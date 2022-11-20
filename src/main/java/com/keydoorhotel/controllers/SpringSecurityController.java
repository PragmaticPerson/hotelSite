package com.keydoorhotel.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.keydoorhotel.service.model.User;
import com.keydoorhotel.service.model.validation.PasswordValidation;
import com.keydoorhotel.service.services.ResetTokenService;
import com.keydoorhotel.service.services.UserService;

@Controller
public class SpringSecurityController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private UserService userService;
	private ResetTokenService tokenService;

	@Autowired
	public SpringSecurityController(UserService userService, ResetTokenService tokenService) {
		super();
		this.userService = userService;
		this.tokenService = tokenService;
	}

	@GetMapping("/password/change")
	public ModelAndView changePasswordGetMapping(@RequestParam("token") String token, ModelMap model) {

		logger.debug("Enter GetMapping /password/change with: token {}", token);

		PasswordValidation validation = tokenService.vaidateToken(token);

		if (validation != PasswordValidation.CORRECT) {
			logger.debug("Token didn't pass validation with error: {}", validation.getName());

			return new ModelAndView("redirect:/login?error=" + validation.getName(), model);
		}

		return new ModelAndView("changePass");
	}

	@PostMapping("/password/change")
	public String changePasswordPostMapping(@RequestParam("password") String password,
			@RequestParam("oldpassword") String oldPassword, @RequestParam("token") String token, Model model) {

		logger.debug("Enter PostMapping /password/change with: token {}, password {}, oldPassword {}", token, password,
				oldPassword);
		User user = tokenService.findByToken(token).getUser();

		logger.debug("Find {}", user);

		if (!userService.isOldPassCorrect(user, oldPassword)) {
			logger.debug("Old password incorrect");
			model.addAttribute("error", "Old password incorrect");
			return "redirect:/password/change?token=" + token;
		}

		userService.updatePassword(user, password);
		logger.debug("Change password success. New password {}", password);
		return "redirect:/";
	}
}
