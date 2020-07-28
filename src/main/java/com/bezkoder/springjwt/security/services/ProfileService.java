package com.bezkoder.springjwt.security.services;

import java.util.Optional;

import com.bezkoder.springjwt.models.Profile;

public interface ProfileService {

	Profile insertProfile(Profile profile);
	Profile updateProfile(Profile profile);
	Optional<Profile> findById(Integer id);
	//Profile findById(long userId);
}
