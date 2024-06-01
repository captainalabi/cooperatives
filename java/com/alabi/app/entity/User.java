package com.alabi.app.entity;


import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.alabi.app.roles.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	private String firstName;	
	
	private String lastName;
	
	private String otherName;
	
	@Column(unique = true)	
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@ManyToAny(fetch = FetchType.LAZY)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"))
	private Collection <Role> roles;
	
	@ManyToMany
	@JoinTable(
			name = "user_cooperatives",
	joinColumns = @JoinColumn(
			name= "user_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(
			name= "cooperative_id", referencedColumnName = "id"))
	private List<Cooperatives> cooperatives;
	
	  @Builder.Default 
	  private boolean enabled = false;
	  
	  @Column(name = "email_verification_token") 
	  private String emailVerificationToken;
	  //private String regVerificationToken;
	 
}
