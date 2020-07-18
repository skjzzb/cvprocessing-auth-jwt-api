package com.bezkoder.springjwt.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.repository.RoleRepository;

@Service
public class RolesServiceImpl implements RolesService {

	@Autowired
	private RoleRepository roleRepoObj;

	@Override
	public List<Role> getAllRoles() {
		 return roleRepoObj.findAll();
	}


}
