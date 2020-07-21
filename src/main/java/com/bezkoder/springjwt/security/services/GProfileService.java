package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.GProfile;

public interface GProfileService {

	GProfile insertProfile(GProfile profile);
	GProfile updateProfile(GProfile profile);
}
