package com.vikas.hotelmanagment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vikas.hotelmanagment.model.Role;
import com.vikas.hotelmanagment.model.User;


@Service
public interface RoleService {
	List<Role> getRoles();
	
	Role createRole (Role theRole);

	void deleteRole (Long id);

	Role findByName(String name);
	
	User removeUserFromRole (Long userId, Long roleId);
	User assignRoleToUser (Long userId, Long roleId);
	
	Role removeAllUsersFromRole(Long roleId);

    Role getRoleById(Long roleid);
}
