package com.app.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Serializable> {
	Optional<UserDetails> findBySaIdNumberOrMobileNumber(String saIdNumber, String mobileNumber);

	Optional<UserDetails> findBySaIdNumberOrMobileNumberOrFirstName(String saIdNumber, String mobileNumber,
			String firstName);
}
