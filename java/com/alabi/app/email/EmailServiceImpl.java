package com.alabi.app.email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alabi.app.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailRepository emailRepository;
	
	@Override
	public Email create(Email email) {
		return emailRepository.save(email);		
	}

	@Override
	public List<Email> read() {
		return emailRepository.findAll();
	}

	@Override
	public Email findById(long id) {		
		return emailRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(long id) {
		emailRepository.deleteById(id);
	}

	/*Checks if email with given token does not exist or already enabled
	 * if not exists or enabled, returns false.
	 * if exists and not enabled, enable it and return true 
	*/
	@Override
	public boolean verify(String token) {
		Email email = emailRepository.findByEmailVerificationToken(token);
		if (email == null || email.isEnabled()) {
			return false;
		} else {
			emailRepository.enable(email.getId());
			return true;
		}
}
	
	public Email findByEmailVerificationToken(String token) {
		return emailRepository.findByEmailVerificationToken(token);
	}

	@Override
	public Email findByEmail(String email) {
		return emailRepository.findByEmail(email);
	}
	
	@Override
	public boolean isEmailVerified(String email) {
		Email emailObject = findByEmail(email);
		if(emailObject != null && emailObject.isEnabled()) {
			return true;
		}
		return false;
	}

}
