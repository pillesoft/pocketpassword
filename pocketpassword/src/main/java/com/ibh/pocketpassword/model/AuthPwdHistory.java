/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibh.pocketpassword.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public final class AuthPwdHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private final Long id;

	@ManyToOne()
	@JoinColumn(name = "authentication_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private final Authentication authentication;

	private final String password;

	private final LocalDateTime expired;

	protected AuthPwdHistory() {
		this(null, null);
	}

	private AuthPwdHistory(Authentication authentication, String password) {
		this.id = null;
		this.authentication = authentication;
		this.password = password;
		this.expired = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public String getPassword() {
		return password;
	}

	public LocalDateTime getExpired() {
		return expired;
	}

	public static class Builder {
		public AuthPwdHistory create(Authentication authentication, String password) {
			return new AuthPwdHistory(authentication, password);
		}
	}
}
