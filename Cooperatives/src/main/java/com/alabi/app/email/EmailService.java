package com.alabi.app.email;

import java.util.List;

public interface EmailService {

	Email create(Email email);
	List<Email> read();
	Email findById(long id);
	void delete(long id);
	boolean verify(String token);
	Email findByEmailVerificationToken(String token);
	Email findByEmail(String email);
	boolean isEmailVerified(String email);
}
