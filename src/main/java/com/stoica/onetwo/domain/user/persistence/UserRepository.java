package com.stoica.onetwo.domain.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.stoica.onetwo.domain.user.User;

interface UserRepository extends JpaRepository<User, String> {
	UserDetails findByLogin(String login);
}
