package com.alabi.app.dto;


import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alabi.app.entity.Cooperatives;

@Component
public class CooperativeDTOMapper{
	
	public CooperativeDTO toDTO(Cooperatives cooperatives) {
		CooperativeDTO cooperativeDTO = new CooperativeDTO();
		cooperativeDTO.setId(cooperatives.getId());
		cooperativeDTO.setLogo(cooperatives.getLogo());
		cooperativeDTO.setCooperativeName(cooperatives.getCooperativeName());
		cooperativeDTO.setCooperativeAddress(cooperatives.getCooperativeAddress());
		cooperativeDTO.setCooperativeEmail(cooperatives.getCooperativeEmail());
		cooperativeDTO.setCooperativePhoneNumber(cooperatives.getCooperativePhoneNumber());
		cooperativeDTO.setCooperativeCACRegNumber(cooperatives.getCooperativeCACRegNumber());
		cooperativeDTO.setUsers(cooperativeDTO.getUsers());
		return cooperativeDTO;
	}
	
	public Cooperatives toCooperatives(CooperativeDTO cooperativeDTO,
			@RequestParam("file") MultipartFile file) throws IOException {
		Cooperatives cooperatives = new Cooperatives();
		cooperatives.setId(cooperativeDTO.getId());
		cooperatives.setLogo(file.getBytes());
		cooperatives.setCooperativeName(cooperativeDTO.getCooperativeName());
		cooperatives.setCooperativeAddress(cooperativeDTO.getCooperativeAddress());
		cooperatives.setCooperativeEmail(cooperativeDTO.getCooperativeEmail());
		cooperatives.setCooperativePhoneNumber(cooperativeDTO.getCooperativePhoneNumber());
		cooperatives.setCooperativeCACRegNumber(cooperativeDTO.getCooperativeCACRegNumber());
		cooperatives.setUsers(cooperativeDTO.getUsers());
		return cooperatives;
	}

}
