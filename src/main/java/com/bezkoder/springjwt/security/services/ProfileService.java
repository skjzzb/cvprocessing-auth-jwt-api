package com.bezkoder.springjwt.security.services;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.springjwt.models.Profile;

public interface ProfileService {

	public Profile update(Profile profileObj);


	Profile insertProfile(Profile profile);
	Profile updateProfile(Profile profile);
	Optional<Profile> findById(Integer id);
	Profile insertProfilePhoto(MultipartFile file);

}
