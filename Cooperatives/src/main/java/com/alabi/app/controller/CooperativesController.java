package com.alabi.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alabi.app.dto.CooperativeDTO;
import com.alabi.app.dto.CooperativeDTOMapper;
import com.alabi.app.entity.Cooperatives;
import com.alabi.app.service.CooperativesService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CooperativesController {

	@Autowired
	private CooperativesService cooperativesService;
	
	@Autowired
	private CooperativeDTOMapper cooperativeDTOMapper; 

	@GetMapping("/test")
	public String test(Model model) {
		return "test";
	}
	
	/*	
	home page
	*/
	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}

	/*
	sign up form
	*/
	@GetMapping("/signup")
	public String signUp(Model model) {
		return "sign_up";
	}	

	/*
	register new coop form
	*/
	@GetMapping("/register_new_cooperative")
	public String registerNewCooperative(Model model) {
		model.addAttribute("cooperativeDTO", new CooperativeDTO());
		return "new_cooperative_form";
	}

	/*
	to join a coop
	*/
	@GetMapping("/join_a_cooperative")
	public String joinACooperative(Model model) {
		
		return "join_a_cooperative";
	}

	/*
	App admin page for admin of all coop
	*/
	@GetMapping("/cooperatives")
	public String allCooperativesPage(Model model) {
		List<Cooperatives> cooperativesList = cooperativesService.listCooperatives();
		model.addAttribute("cooperativesList", cooperativesList);
		return "cooperatives";
	}

	/*
	register new coop
	*/
	@PostMapping("/create")
	public String create(CooperativeDTO cooperativeDTO, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file)			
			throws IOException {		
		Cooperatives cooperatives = new Cooperatives();	
		StringBuilder message = new StringBuilder();
		if(cooperativeDTO.getId() != 0) {
			cooperatives = cooperativesService.
					findCooperativesByID(cooperativeDTO.getId()).orElse(null);			
			cooperatives = cooperativeDTOMapper.toCooperatives(cooperativeDTO, file);		
			cooperativesService.create(cooperatives);
			message.append("Cooperative Updated Successfully!");
		}else {			
			cooperatives = cooperativeDTOMapper.toCooperatives(cooperativeDTO, file);		
			cooperativesService.create(cooperatives);
			message.append("New Cooperative Created!");
		}				
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/cooperatives";
	}

	/*		
	link to show logo
	*/
	@GetMapping("/image")
	public void showImage(@RequestParam("id") long id, HttpServletResponse response,
			Optional<Cooperatives> cooperatives) throws IOException {
		cooperatives = cooperativesService.findCooperativesByID(id); 
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf");
		response.getOutputStream().write(cooperatives.get().getLogo());
		response.getOutputStream().close();
	}

	/*	
	get the update form(use the new_cooperative_form)
	*/
	@GetMapping("/update")
	public String update(@RequestParam long id, Model model) {
		Cooperatives cooperatives = cooperativesService.findCooperativesByID(id).get();
		CooperativeDTO cooperativeDTO = cooperativeDTOMapper.toDTO(cooperatives);		
		model.addAttribute("cooperativeDTO", cooperativeDTO);		
		return "new_cooperative_form";
	}
	
	/*	
	delete a cooperative account completely
	*/
	@GetMapping("/delete")
	public String delete(@RequestParam long id, RedirectAttributes redirectAttributes) {		
		redirectAttributes.addFlashAttribute("message",cooperativesService.delete(id));
		return "redirect:/cooperatives";
	}
}
