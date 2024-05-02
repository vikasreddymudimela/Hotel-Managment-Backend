package com.vikas.hotelmanagment.security.user;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.vikas.hotelmanagment.model.User;
import com.vikas.hotelmanagment.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@ComponentScan("com.vikas.hotelmanagment.secuirty.jwt.user")
@Component
public class HotelUserDetailsService implements UserDetailsService {
  private final UserRepository userrepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = userrepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return HotelUserDetails.buildUserDetails(user);
	}
	

}
