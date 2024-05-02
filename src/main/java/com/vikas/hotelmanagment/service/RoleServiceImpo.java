package com.vikas.hotelmanagment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vikas.hotelmanagment.Exception.RoleAlreadyExistException;
import com.vikas.hotelmanagment.Exception.UserAlreadyExistsException;
import com.vikas.hotelmanagment.Exception.UsernameNotFoundException;
import com.vikas.hotelmanagment.model.Role;
import com.vikas.hotelmanagment.model.User;
import com.vikas.hotelmanagment.repository.RoleRepository;
import com.vikas.hotelmanagment.repository.UserRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class RoleServiceImpo implements RoleService{
	private final RoleRepository rolerepository;
	private final UserRepository userrepository;

	@Override
	public List<Role> getRoles() {
		// TODO Auto-generated method stub
		return  rolerepository.findAll();
	}

	@Override
	public Role createRole(Role theRole) {
		// TODO Auto-generated method stub
		System.out.println("came here to the role 3");
		System.out.println(theRole.getName());
		String roleName = "ROLE_"+theRole.getName().toUpperCase();
		System.out.println("came here to the role 4");
		System.out.println(roleName);
		Role role = new Role(roleName);
		System.out.println("came here to the role 5");
		System.out.println(role.getName());
		if (rolerepository.existsByName (role.getName())){
			throw new RoleAlreadyExistException(theRole.getName()+" role already exists");
			}
		return rolerepository.save(role);
	}

	@Override
	public void deleteRole(Long roleId) {
		// TODO Auto-generated method stub
		this.removeAllUsersFromRole(roleId);
		rolerepository.deleteById(roleId);
		
	}

	@Override
	public Role findByName(String name) {
		// TODO Auto-generated method stub
		return rolerepository.findByName(name).get();
	}

	@Override
	public User removeUserFromRole(Long userId, Long roleId) {
		// TODO Auto-generated method stub
		Optional<User> user = userrepository.findById(userId);
		Optional<Role> role = rolerepository.findById(roleId);
		if(role.isPresent() && role.get().getUsers().contains(user.get())) {
			role.get().removeuserfromrole(user.get());
			rolerepository.save(role.get());
			userrepository.save(user.get());
			return user.get();
		}
		throw new UsernameNotFoundException("User not found in role");
	}

	@Override
	public User assignRoleToUser(Long userId, Long roleId) {
		// TODO Auto-generated method stub
		Optional<User> user = userrepository.findById(userId);
		Optional<Role> role = rolerepository.findById(roleId);
		if(user.isPresent() && user.get().getRoles().contains(role.get())) {
			throw new UserAlreadyExistsException(user.get().getFirstname()+" is already assigned to the "+role.get().getName()+" role");
		}
		if(role.isPresent()) {
			role.get().assignroletouser(user.get());
			rolerepository.save(role.get());
		}
		return user.get();
	}

	@Override
	public Role removeAllUsersFromRole(Long roleId) {
		// TODO Auto-generated method stub
		Optional<Role> role = rolerepository.findById(roleId);
		if(role.isPresent()) {
			role.get().removeallusersfromrole();
		}
		return rolerepository.save(role.get());
	}

	@Override
	public Role getRoleById(Long roleid) {
		// TODO Auto-generated method stub
		return rolerepository.findById(roleid).get();
	}

}
