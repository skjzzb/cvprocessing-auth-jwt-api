package com.bezkoder.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.GUser;

@Repository
public interface GUserRepository extends JpaRepository<GUser, Long> {
	Optional<GUser> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByName(String name);
}
