package com.vikas.hotelmanagment.Listener;

import com.vikas.hotelmanagment.model.Role;
import com.vikas.hotelmanagment.model.User;
import com.vikas.hotelmanagment.repository.RoleRepository;
import com.vikas.hotelmanagment.repository.UserRepository;
import com.vikas.hotelmanagment.service.RoleService;
import com.vikas.hotelmanagment.service.Userservice;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartupListener {

    private final RoleRepository rolerepo;
   

    

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        createRolesIfNotExist();
  
      
//       role.assignroletouser(user);
//       roleservice.assignRoleToUser(user.getId(), role.getId());
          
     
        
    }

    private void createRolesIfNotExist() {
        createRoleIfNotExist("ROLE_USER");
        createRoleIfNotExist("ROLE_ADMIN");
    }

    private void createRoleIfNotExist(String roleName) {
    Role role = new Role(roleName);
    if(rolerepo.existsByName(roleName)) {
    	return;
    }
    rolerepo.save(role);
    }
}
