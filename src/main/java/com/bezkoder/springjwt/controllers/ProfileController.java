package com.bezkoder.springjwt.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;


import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.springjwt.models.ERole;
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
	
	@GetMapping("/image/{profId}")  
	public ResponseEntity<?> getProfileImage(@PathVariable Integer profId) throws FileNotFoundException
	{
		byte[] media=null;
	Optional<Profile> p =profServiceObj.findById(profId);
	Profile pro= p.get();
	System.out.println(pro.getProfilePicture());
	if(pro.getProfilePicture()!=null)
	{
	FileInputStream fin = new FileInputStream(pro.getProfilePicture());  
    BufferedInputStream bin = new BufferedInputStream(fin);  
		    try {
				 media = IOUtils.toByteArray(bin);
			} catch (IOException e) {
				System.out.println("-------------------");
				e.printStackTrace();
			}
		    return new ResponseEntity<>(media, HttpStatus.OK);
	}else
	{
		return new ResponseEntity<>("image not uploaded", HttpStatus.NOT_FOUND);	
	}
	}
	
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
	
	public String createFolder()
	{
		System.out.println(System.getProperty("java.io.tmpdir"));
	    final String baseTempPath = System.getProperty("java.io.tmpdir");

	    File tempDir = new File(baseTempPath + File.separator + "images");
	    if (tempDir.exists() == false) {
	        tempDir.mkdir();
	    }
	   System.out.println(tempDir);
	   return tempDir.getPath();
	}

	  @PutMapping("/upload/{profId}")
	public ResponseEntity<?> uplaodImage(@RequestParam("imageFile") MultipartFile file,@PathVariable Integer profId) throws IOException 
	  {
	    String systemPath=createFolder();
	    System.out.println(systemPath);
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
		  
		  File convertFile = new File(systemPath+File.separator+file.getOriginalFilename());
		  convertFile.createNewFile();
		  FileOutputStream fout = new FileOutputStream(convertFile);
		  fout.write(file.getBytes());
		  fout.close();
		  
		  
		  p.setProfilePicture(systemPath+File.separator+file.getOriginalFilename());
		 
		  profileRep.save(p);
		  //profServiceObj.insertProfilePhoto(file);
		  
		  System.out.println("successful");
		  return new ResponseEntity<>("Saved successfully",HttpStatus.OK); 
	  
	      }
	  
	    //http://localhost:8880/api/get-all-users/panel
	    @GetMapping("/get-all-users/panel")
		ResponseEntity<?> getAllUsersPanel()
		{
	    	Set<Role> roleSet = new HashSet<Role>();
	    	Role role = new Role();
	    	role.setId(5);
	    	role.setName(ERole.ROLE_PANEL);
	    	roleSet.add(role);
	    	List <User> userList = userRepository.findByRoles(roleSet);
	    	return new ResponseEntity<>(userList, HttpStatus.OK);
		}
	    
        //http://localhost:8880/api/get-all-users/hr
	    @GetMapping("/get-all-users/hr")
	    ResponseEntity<?> getAllUsersHr()
	    {
	    	Set<Role> roleSet = new HashSet<Role>();
	    	Role role = new Role();
	    	role.setId(4);
	    	role.setName(ERole.ROLE_HR);
	    	roleSet.add(role);
	    	List <User> userList = userRepository.findByRoles(roleSet);
	    	return new ResponseEntity<>(userList, HttpStatus.OK);
	    }

	    @DeleteMapping("/profileDelete")
		public ResponseEntity<?> deleteProfile(@RequestParam long userId) {
	    	Integer profId=null;
	    	
	    	System.out.println("in profile delete controller");
			User user = userRepository.findById(userId).orElse(null);
			System.out.println("user is :"+user);
			userRepository.deleteById(userId);
			profId = user.getProfile().getProfId();
			System.out.println(profId);
			profileRep.deleteById(profId);
			
			return new ResponseEntity<>("sucessfully deleted", HttpStatus.OK);
			
	    }
}
