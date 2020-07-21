package com.bezkoder.springjwt.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bezkoder.springjwt.models.GProfile;
import com.bezkoder.springjwt.repository.GProfileRepository;

@Service

public class GProfileServiceImpl implements GProfileService {

	@Autowired
	GProfileRepository profileRepoObj;

	@Override
	public GProfile insertProfile(GProfile profile) {
		profileRepoObj.save(profile);
		return profileRepoObj.save(profile);
	}

	@Override
	public GProfile updateProfile(GProfile profile) {
		profileRepoObj.save(profile);
		return profileRepoObj.save(profile);
	}
 
}
