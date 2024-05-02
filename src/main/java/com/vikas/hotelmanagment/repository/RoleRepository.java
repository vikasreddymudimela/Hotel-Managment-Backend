package com.vikas.hotelmanagment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikas.hotelmanagment.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

	Optional<Role> findByName(String role);

	boolean existsByName(String rolename);

}
