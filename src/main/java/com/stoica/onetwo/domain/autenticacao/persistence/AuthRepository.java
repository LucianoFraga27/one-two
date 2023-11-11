package com.stoica.onetwo.domain.autenticacao.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.stoica.onetwo.domain.autenticacao.AuthModel;

interface AuthRepository extends JpaRepository<AuthModel, Long> {
	UserDetails findByLogin(String login);
}
