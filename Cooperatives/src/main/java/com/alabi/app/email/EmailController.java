package com.alabi.app.email;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alabi.app.networkConnection.NetworkLatencyChecker;
import com.alabi.app.utility.EmailMailSenderService;
import com.alabi.app.utility.SiteURL;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EmailController {

	@Autowired
	private NetworkLatencyChecker networkLatencyChecker;
	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailMailSenderService emailMailSender;
	
	@GetMapping("/create_email_form")
	public String createEmailForm(Model model) {
		model.addAttribute("emailObject", new Email());
		return "email/email_form";
	}
	
	@PostMapping("/create_email")
	public String createEmail(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String message = "";
		String warning = "";
		String email = request.getParameter("email");
		String id = request.getParameter("id");
		long longId = Long.valueOf(id);		
		Email emailObject = new Email();
		try {
		if(longId !=0) {
			emailObject = emailService.findById(longId);
			emailObject.setEmail(email);
			emailService.create(emailObject);
			message = "Success\n Email Edited Successfully";
			redirectAttributes.addFlashAttribute("message", message);			
		}
		if(!networkLatencyChecker.isNetworkLatent("www.google.com")) {					
			message = "Email Submitted!\n Please Check Your Inbox To Verify";
			//send verification email
			//send verification code to verify email
			String siteUrl = SiteURL.getURL(request);	
			String verificationToken = UUID.randomUUID().toString();
			emailObject.setEmailVerificationToken(verificationToken);
			String url = siteUrl + "/verifyEmail?token="+ verificationToken;	
			emailObject.setEmail(email);
			emailService.create(emailObject);
		String subject = "Here's The Link To Confirm Your Email.";
		String body = "<p>Hello,</p>"
					+ "<p>Please, click the link below to complete your registration:</p>"
					+ "<p><b><a href=\"" + url + "\">Confirm Your Email</a></b></p>"
					+ "<p>Ignore this mail if you are not registering.</p>";				
		emailMailSender.sendEmail(email, subject, body);
			redirectAttributes.addFlashAttribute("message", message);
		}else {
			message = "Network Latency Error! Please Try Again.";
			redirectAttributes.addFlashAttribute("message", message);
		}		
		}catch(DataIntegrityViolationException e) {
			warning = "Duplicate Entry!\n Please try again.";
			redirectAttributes.addFlashAttribute("warning", warning);
		} catch (AddressException e) {
			delete(emailObject.getId());
			warning = "Error! Improper Email Address. Try Again";
			redirectAttributes.addFlashAttribute("warning", warning);
		} catch (MessagingException e) {
			delete(emailObject.getId());
			warning = "Error! Internet Error. Try Again";
			redirectAttributes.addFlashAttribute("warning", warning);
		}		
		return "redirect:/create_email_form";
	}
	
	@GetMapping("/read_email")
	public String readEmail( Model model) {		
		model.addAttribute("emails", emailService.read());	 
		return "email/email_list";
	}
	
	@GetMapping("/update_email")
	public ModelAndView showUpdateForm(@RequestParam Long id) {
		ModelAndView mav = new ModelAndView("email/email_form");
		mav.addObject("emailListList", emailService.read());
		Email email = emailService.findById(id);
		mav.addObject("emailObject", email);
		return mav;
	}
	
	@GetMapping("/delete_email")
	public String delete(@RequestParam Long id) {
		emailService.delete(id);
		return "redirect:/read_email";
	}
	
	@GetMapping("/verifyEmail")
	public String verifyEmail(@Param("token") String token, RedirectAttributes redirectAttributes) {
		boolean verified = emailService.verify(token);
		String pageTitle = verified ? "Registration Successful!" : "Registration Failed!";
		redirectAttributes.addFlashAttribute("pageTitle", pageTitle);
		System.out.println("::::::::::::::::::: verified::::::::::::::: " + verified);
		return verified ? "email/verified_success" : "email/verified_failed";
	}
	
	@GetMapping("/verifySuccess")//to change password
	public String verifySuccessPage() {		
		return "email/verified_success";
	}
	
	@GetMapping("/continue_registration")//to change password
	public String continueRegostration() {		
		return "redirect:/addnewuser";
	}
}
