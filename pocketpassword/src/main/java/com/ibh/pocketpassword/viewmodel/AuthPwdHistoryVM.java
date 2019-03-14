package com.ibh.pocketpassword.viewmodel;

import java.time.LocalDateTime;

import com.ibh.pocketpassword.model.Authentication;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class AuthPwdHistoryVM {

	private final ObjectProperty<Authentication> authentication;
	private final StringProperty password;
	private final ObjectProperty<LocalDateTime> expired;

	public AuthPwdHistoryVM(Authentication authentication, String password) {
		this.authentication = new SimpleObjectProperty<Authentication>(this, "authentication", authentication);
		this.password = new SimpleStringProperty(this, "password", password);
		this.expired = new SimpleObjectProperty<LocalDateTime>(this, "expired", LocalDateTime.now());
	}
	
	public AuthPwdHistoryVM(LocalDateTime expired, String password) {
		this.authentication = new SimpleObjectProperty<Authentication>(this, "authentication", null);
		this.expired = new SimpleObjectProperty<LocalDateTime>(this, "expired", expired);
		this.password = new SimpleStringProperty(this, "password", password);
	}

	public ObjectProperty<Authentication> getAuthentication() {
		return authentication;
	}
	
	public StringProperty getPassword() {
		return password;
	}

	public ObjectProperty<LocalDateTime> getExpired() {
		return expired;
	}

}
