package com.alabi.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alabi.app.dto.UserDTO;
import com.alabi.app.email.Email;
import com.alabi.app.email.EmailService;
import com.alabi.app.service.RoleService;
import com.alabi.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	private final UserService userService;
	@Autowired
	private final RoleService roleService;		
	@Autowired
	private EmailService emailService;
		
	@GetMapping("/login")
	public String login() {		
		return "login";
	}

	/*
	after successful log in or sign up
	*/
	@GetMapping("/login_success")
	public String loginSuccess(Model model) {
		return "login_success";
	}
	
	@GetMapping({ "/list-dto", "/list" })
	public ModelAndView listUser() {
		ModelAndView mav = new ModelAndView("list-users");
		List<UserDTO> users = userService.read();
		mav.addObject("users", users);
		return mav;
	}

	@GetMapping("/addnewuser")
	public ModelAndView addUserForm() {
		ModelAndView mav = new ModelAndView("register-user-form");		
		mav.addObject("userDTO", new UserDTO());
		mav.addObject("roleList", roleService.readRole());
		return mav;
	}
		
	@PostMapping("/saveUser")
	public String saveUser(UserDTO userDTO, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		String successMessage = "";
		try {
			if (userDTO.getId() != null) {
				userService.edit(userDTO);
				successMessage = "User Edit successful";
			} 
				//check if user email is verified before registration
				if(emailService.isEmailVerified(userDTO.getEmail())) {
					userService.create(userDTO);
					System.out.println("::::::::::::::::::::::::::::: successful");
					successMessage = "Registration Successful!";
				}						
		} catch (DataIntegrityViolationException e) {
			successMessage = "Duplicate Registration, Please Try Again!";
		}
		redirectAttributes.addFlashAttribute("successMessage", successMessage);
		return "redirect:/addnewuser";
	}

	@GetMapping("/showUpdateForm")
	public ModelAndView showUpdateForm(@RequestParam Long userId) {
		ModelAndView mav = new ModelAndView("register-user-form");
		mav.addObject("roleList", roleService.readRole());
		UserDTO userDTO = userService.findById(userId);
		mav.addObject("userDTO", userDTO);
		return mav;
	}

	@GetMapping("/deleteUser")
	public String deleteEmployee(@RequestParam Long userId) {
		userService.deleteById(userId);
		return "redirect:/list";
	}	
}
