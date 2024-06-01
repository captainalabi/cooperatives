package com.alabi.app.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="cooperatives")
public class Cooperatives {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;	
	private byte [] logo;
	private String cooperativeName;
	private String cooperativeAddress;
	private String cooperativeEmail;
	private String cooperativePhoneNumber;
	private String cooperativeCACRegNumber;
	@ManyToMany(mappedBy = "cooperatives")
	private List<User> users;
}
