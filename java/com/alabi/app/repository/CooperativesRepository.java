package com.alabi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alabi.app.entity.Cooperatives;

@Repository
public interface CooperativesRepository extends JpaRepository<Cooperatives, Long>{

}
