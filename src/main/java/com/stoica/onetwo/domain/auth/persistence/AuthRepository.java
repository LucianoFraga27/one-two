package com.stoica.onetwo.domain.auth.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.stoica.onetwo.domain.auth.AuthModel;

interface AuthRepository extends JpaRepository<AuthModel, Long> {
	UserDetails findByLogin(String login);
}
