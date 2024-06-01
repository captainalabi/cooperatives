package com.alabi.app.service;

import java.util.List;
import java.util.Optional;

import com.alabi.app.entity.Cooperatives;

public interface CooperativesService {
	
	Cooperatives create(Cooperatives cooperatives);
		
	List<Cooperatives> listCooperatives() ;
	
	Optional<Cooperatives> findCooperativesByID(long id);
	 
	String delete(long id);
}
