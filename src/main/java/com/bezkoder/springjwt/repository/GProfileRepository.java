package com.bezkoder.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.GProfile;

@Repository
public interface GProfileRepository extends JpaRepository<GProfile, Integer> {

}
