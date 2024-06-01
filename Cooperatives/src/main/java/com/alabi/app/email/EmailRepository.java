package com.alabi.app.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

	@Query("SELECT e from Email e where e.emailVerificationToken=?1") 
	  Email findByEmailVerificationToken(String token);	
	
	@Query("SELECT e from Email e where e.email=?1") 
	  Email findByEmail(String email);	
	
	 @Query("UPDATE Email e set e.enabled=true where e.id=?1")	  
	 @Modifying	  
	 @Transactional 
	 void enable(Long id);
}
