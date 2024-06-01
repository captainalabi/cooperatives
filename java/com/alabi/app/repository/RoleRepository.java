package com.alabi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alabi.app.roles.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
