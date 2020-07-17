package com.bezkoder.springjwt.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.zip.Deflater;

import javax.persistence.Access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.springjwt.models.Profile;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.ProfileRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.services.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;


import io.swagger.models.Response;

@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProfileController {

	private static final String USER_IDENTIFIER_KEY = null;

	@Autowired
	ProfileService profServiceObj;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	UserRepository userRepository;
	
	@PutMapping("/profile/{userId}")
	public ResponseEntity<?> editProfile(@RequestBody Profile profile, @PathVariable long userId) {

		User user = userRepository.findById(userId).orElse(null);
		System.out.println("user is "+user);
		if (user != null) {
			Profile existedProfile = user.getProfile();
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

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable long userId) {
		return new ResponseEntity<>(userRepository.findById(userId), HttpStatus.OK);
	}
 

	  @PutMapping("/upload/{profId}")
	  
	public ResponseEntity<?> uplaodImage(@RequestParam("imageFile") MultipartFile file,@PathVariable Integer profId) throws IOException 
	  {
	      //Optional<Profile> val = profServiceObj.findById(profId);
		  Optional<Profile> val = profServiceObj.findById(profId);
	      if (val.isPresent()) {
	            System.out.println(val);
	        } else {
	            System.out.printf("No found with id %d%n", profId);
	        }
		  
		  System.out.println("Original Image Byte Size - " + file.getBytes().length);
		  
		  Profile img = new Profile(file.getOriginalFilename(),profId);
		  System.out.println(img);
		  
		  System.out.println("File name : "+file.getOriginalFilename());
		 
		  File convertFile = new File("/home/hp/FileUpload/"+file.getOriginalFilename());
		  convertFile.createNewFile();
		  FileOutputStream fout = new FileOutputStream(convertFile);
		  fout.write(file.getBytes());
		  fout.close();
		  
		  Profile p = val.get();
		  p.setProfilePicture(file.getOriginalFilename());
		  
		  profileRepository.save(p);
		  //profServiceObj.insertProfilePhoto(file);
		  
		  System.out.println("successful");
		  return new ResponseEntity<>("Saved successfully",HttpStatus.OK); 
	  
	      }

	

	  }

