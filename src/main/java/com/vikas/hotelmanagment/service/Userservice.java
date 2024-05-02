package com.vikas.hotelmanagment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vikas.hotelmanagment.model.User;

@Service
public interface Userservice {
 User registeruser(User  user);
 List<User> getusers();
 void deleteuser(String email);
 User getuser(String email);
}
