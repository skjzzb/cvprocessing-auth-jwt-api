package com.bezkoder.springjwt.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.springjwt.models.Profile;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	ProfileRepository profileRepoObj;

	
	@Override
	public Profile update(Profile profileObj) {
		
		return profileRepoObj.save(profileObj);
	}
	
	public void updateProfilePhoto(MultipartFile file) {
		profileRepoObj.save(file);
	}

	@Override
	public Optional<Profile> findById(Integer id) {
		return profileRepoObj.findById(id);
		/*Profile user = profileRepoObj.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with userid: " + id));
		
		return user;*/
	}

	@Override
	public Profile insertProfilePhoto(MultipartFile file) {
		profileRepoObj.save(file);
		return profileRepoObj.save(file);
	}

	@Override
	public Profile insertProfile(Profile profile) {
		profileRepoObj.save(profile);
		return profileRepoObj.save(profile);
	}

	@Override
	public Profile updateProfile(Profile profile) {
		profileRepoObj.save(profile);
		return profileRepoObj.save(profile);
	}

	
}
