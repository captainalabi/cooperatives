package com.alabi.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alabi.app.entity.Cooperatives;
import com.alabi.app.repository.CooperativesRepository;

@Service
public class CooperativeServiceImpl implements CooperativesService {

	@Autowired
	private CooperativesRepository cooperativesRepository;
	
	@Override
	public Cooperatives create(Cooperatives cooperatives) {
		return cooperativesRepository.save(cooperatives);
	}
	
	@Override
	public List<Cooperatives> listCooperatives() {
	return cooperativesRepository.findAll();	
	}
	
	@Override
	public Optional<Cooperatives> findCooperativesByID(long id) {
		return cooperativesRepository.findById(id);
	} 
}
