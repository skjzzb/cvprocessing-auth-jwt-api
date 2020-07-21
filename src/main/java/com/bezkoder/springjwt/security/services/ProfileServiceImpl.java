package com.bezkoder.springjwt.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bezkoder.springjwt.models.Profile;
import com.bezkoder.springjwt.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {
	@Autowired
	ProfileRepository profileRepoObj;

	
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

	@Override
	public Optional<Profile> findById(Integer id) {
		return profileRepoObj.findById(id);
	}

}
