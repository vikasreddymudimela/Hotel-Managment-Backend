package com.vikas.hotelmanagment.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vikas.hotelmanagment.Exception.UserAlreadyExistsException;
import com.vikas.hotelmanagment.Exception.UsernameNotFoundException;
import com.vikas.hotelmanagment.model.Role;
import com.vikas.hotelmanagment.model.User;
import com.vikas.hotelmanagment.repository.RoleRepository;
import com.vikas.hotelmanagment.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpo implements Userservice{
 private final UserRepository userrepo;
 private final RoleRepository rolerepo;
 private final PasswordEncoder passwordencoder;
	@Override
	public User registeruser(User user) {
		// TODO Auto-generated method stub
		if(userrepo.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistsException(user.getEmail()+"already exists");
		}
		user.setPassword(passwordencoder.encode(user.getPassword()));
		Role userRole = rolerepo.findByName("ROLE_USER").get();
		user.setRoles(Collections.singletonList(userRole));
		return userrepo.save(user);
	}

	@Override
	public List<User> getusers() {
		// TODO Auto-generated method stub
		return userrepo.findAll();
	}
    
	
	@Transactional
	@Override
	public void deleteuser(String email) {
		// TODO Auto-generated method stub
		User user = getuser(email);
		if(user!=null) {
			userrepo.deleteByEmail(email);
		}
		
	}

	@Override
	public User getuser(String email) {
		// TODO Auto-generated method stub
		return userrepo.findByEmail(email).orElseThrow(() -> new  UsernameNotFoundException("User not found in database"));
	}

}
