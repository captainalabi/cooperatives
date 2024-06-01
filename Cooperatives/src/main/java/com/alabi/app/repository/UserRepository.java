package com.alabi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alabi.app.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email= :email")
	User findByEmail(@Param("email") String email);
	
	  @Query("UPDATE User u set u.enabled=true where u.id=?1")	  
	  @Modifying	  
	  @Transactional 
	  void enable(Long id);
	  
	  @Query("SELECT u from User u where u.emailVerificationToken=?1") 
	  User findByEmailVerificationToken(String token);	 
}
