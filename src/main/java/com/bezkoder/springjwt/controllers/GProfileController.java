package com.bezkoder.springjwt.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.springjwt.models.GProfile;
import com.bezkoder.springjwt.models.GUser;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.repository.GUserRepository;
import com.bezkoder.springjwt.security.services.GProfileService;
import com.bezkoder.springjwt.security.services.RolesService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/gapi")
public class GProfileController {

	@Autowired
	GProfileService profServiceObj;

	@Autowired
	GUserRepository userRepository;
	
	@Autowired
	RolesService roleServiceObj;

	@PutMapping("/profile/{userId}")
	public ResponseEntity<?> editProfile(@RequestBody GProfile profile, @PathVariable long userId) {

		GUser user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			GProfile existedProfile = user.getProfile();
			if (existedProfile == null) {
				profServiceObj.insertProfile(profile);
				user.addProfile(profile);
				return new ResponseEntity<>(profServiceObj.insertProfile(profile), HttpStatus.OK);
			} else {
				existedProfile.setAboutMe(profile.getAboutMe());
				existedProfile.setAddress(profile.getAddress());
				existedProfile.setCity(profile.getCity());
				existedProfile.setCountry(profile.getCountry());
				existedProfile.setPinCode(profile.getPinCode());
				existedProfile.setState(profile.getState());
				existedProfile.setContactNo(profile.getContactNo());
				profServiceObj.updateProfile(existedProfile);
				return new ResponseEntity<>(profServiceObj.updateProfile(existedProfile), HttpStatus.OK);
			}

		}
		return new ResponseEntity<>("User not found", HttpStatus.OK);
	}

//	@GetMapping("/user/{userId}")
//	public ResponseEntity<?> getUserById(@PathVariable long userId) {
//		return new ResponseEntity<>(userRepository.findById(userId), HttpStatus.OK);
//	}
	
	@GetMapping("/user/{userName}")
	public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
		return new ResponseEntity<>(userRepository.findByUsername(userName), HttpStatus.OK);
	}
	
	
	//http://localhost:8880/api/roles
	@GetMapping("/user")
	public ResponseEntity<?> getAllUsers() {
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/roles")
	public ResponseEntity<?> getListOfRoles(){
		return new ResponseEntity<>(roleServiceObj.getAllRoles(), HttpStatus.OK);
	}
	
	@PostMapping("/set-role/{userId}")
	public ResponseEntity<?> setRole(@RequestBody Role role, @PathVariable long userId  ){
		GUser u = userRepository.findById(userId).orElse(null);
		System.out.println(u);
			if(u != null) {
			Set<Role> roleSet = new HashSet<Role>();
			roleSet.add(role);
			u.setRoles(roleSet);
			userRepository.save(u);
		}
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

}
