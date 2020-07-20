package com.bezkoder.springjwt.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.ProfileRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.services.ProfileService;
import com.bezkoder.springjwt.security.services.RolesService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProfileController {

	@Autowired
	ProfileService profServiceObj;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RolesService roleServiceObj;
	
	@Autowired
	ProfileRepository profileRep;

	@PutMapping("/profile/{userId}")
	public ResponseEntity<?> editProfile(@RequestBody Profile profile, @PathVariable long userId) {

		User user = userRepository.findById(userId).orElse(null);
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
		User u = userRepository.findById(userId).orElse(null);
		System.out.println(u);
			if(u != null) {
			Set<Role> roleSet = new HashSet<Role>();
			roleSet.add(role);
			u.setRoles(roleSet);
			userRepository.save(u);
		}
		return new ResponseEntity<>(u, HttpStatus.OK);
	}
	
	@Value("${file.path}")
	private String filepath;

	  @PutMapping("/upload/{profId}")
	public ResponseEntity<?> uplaodImage(@RequestParam("imageFile") MultipartFile file,@PathVariable Integer profId) throws IOException 
	  {
	      //Optional<Profile> val = profServiceObj.findById(profId);
		  System.out.println("-------------------"+profId);
		  Optional<Profile> val = profServiceObj.findById(profId);
	      if (val.isPresent()) {
	            System.out.println(val);
	        } else {
	            System.out.printf("No found with id %d%n", profId);
	        }
		  
	      Profile p = val.get();
	      
		  System.out.println("Original Image Byte Size - " + file.getBytes().length);
		  
		  System.out.println("File name : "+file.getOriginalFilename());
		  
		  File convertFile = new File(filepath+file.getOriginalFilename());
		  convertFile.createNewFile();
		  FileOutputStream fout = new FileOutputStream(convertFile);
		  fout.write(file.getBytes());
		  fout.close();
		  
		  
		  p.setProfilePicture(filepath+file.getOriginalFilename());
		 
		  
		  profileRep.save(p);
		  //profServiceObj.insertProfilePhoto(file);
		  
		  System.out.println("successful");
		  return new ResponseEntity<>("Saved successfully",HttpStatus.OK); 
	  
	      }


}
