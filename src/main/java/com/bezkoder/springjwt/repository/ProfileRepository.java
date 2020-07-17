package com.bezkoder.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.springjwt.models.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>{

	Profile save(MultipartFile file);

}
