package com.vikas.hotelmanagment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vikas.hotelmanagment.model.Role;
import com.vikas.hotelmanagment.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	boolean existsByEmail(String email);

	void deleteByEmail(String email);
    
//@Query(value="select * from user where email =?",nativeQuery=true)
	Optional<User> findByEmail(String email);

}
