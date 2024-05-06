package com.alabi.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alabi.app.entity.Cooperatives;
import com.alabi.app.service.CooperativesService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CooperativesController {

	@Autowired
	private CooperativesService cooperativesService;
		
	@GetMapping("/")//sign up or login
	public String home(Model model) {
		return "index";
	}
	
	@GetMapping("/login_success")//sign up or login
	public String loginSuccess(Model model) {
		return "login_success";
	}
	
	@GetMapping("/register_new_cooperative")
	public String registerNewCooperative(Model model) {
		model.addAttribute("cooperatives", new Cooperatives());
		return "new_cooperative_form";
	}
	@GetMapping("/join_a_cooperative")
	public String joinACooperative(Model model) {
		
		return "join_a_cooperative";
	}
	
	@GetMapping("/cooperatives")
	public String allCooperativesPage(Model model) {
		List<Cooperatives> cooperativesList = cooperativesService.listCooperatives();
		model.addAttribute("cooperativesList", cooperativesList);
		return "cooperatives";
	}
	
	@PostMapping("/create")
	public String fileUpload(Model model, @RequestParam("file") MultipartFile file,
			@RequestParam("cooperativeName") String cooperativeName,
			@RequestParam("cooperativeAddress") String cooperativeAddress,
			@RequestParam("cooperativeEmail") String cooperativeEmail,
			@RequestParam("cooperativePhoneNumber") String cooperativePhoneNumber,
			@RequestParam("cooperativeCACRegNumber") String cooperativeCACRegNumber) 
			throws IOException {
		
		Cooperatives cooperatives = new Cooperatives();		
		cooperatives.setLogo(file.getBytes());
		cooperatives.setCooperativeName(cooperativeName);	
		cooperatives.setCooperativeAddress(cooperativeAddress);
		cooperatives.setCooperativeEmail(cooperativeEmail);
		cooperatives.setCooperativePhoneNumber(cooperativePhoneNumber);
		cooperatives.setCooperativeCACRegNumber(cooperativeCACRegNumber);		
		cooperativesService.create(cooperatives);
		model.addAttribute("success", "File uploaded successfully!");
		return "redirect:/cooperatives";
	}
	
	@GetMapping("/image")
	public void showImage(@RequestParam("id") long id, HttpServletResponse response,
			Optional<Cooperatives> cooperatives) throws IOException {
		cooperatives = cooperativesService.findCooperativesByID(id); 
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf");
		response.getOutputStream().write(cooperatives.get().getLogo());
		response.getOutputStream().close();
	}
}
