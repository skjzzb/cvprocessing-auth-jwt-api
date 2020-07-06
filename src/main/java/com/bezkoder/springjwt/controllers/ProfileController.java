package com.bezkoder.springjwt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bezkoder.springjwt.constant.Constant;
import com.bezkoder.springjwt.models.Profile;
import com.bezkoder.springjwt.security.services.ProfileService;




@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ProfileController {

	@Autowired
	private ProfileService ServiceObj;
	
	
	//@PostMapping(value = Constant.ADD_PROFILE ,headers = Constant.ACCEPT_JSON)
	@PostMapping("/profile")
	public ResponseEntity<Void> updateCandidateInfo(@RequestBody Profile profileObj )
	{
		ServiceObj.update(profileObj);
		System.out.println("Added");
		return new ResponseEntity<Void>(HttpStatus.FOUND);
	}

}
