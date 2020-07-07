package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.Profile;

public interface ProfileService {

	Profile insertProfile(Profile profile);
	Profile updateProfile(Profile profile);
}
