/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibh.pocketpassword.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public final class AuthProperty implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private final Long id;

  @ManyToOne()
  @JoinColumn(name = "authentication_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private final Authentication authentication;
    
  private final String propertyName;
  
  private final String propertyValue;
  
  public Long getId() {
    return id;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public String getPropertyValue() {
    return propertyValue;
  }

  public Authentication getAuthentication() {
    return authentication;
  }
  
  private AuthProperty() {
	this(null, null, null, null);
  }

  private AuthProperty(Long id, Authentication authentication, String propertyName, String propertyValue) {
    this.id = id;
	this.authentication = authentication;
    this.propertyName = propertyName;
    this.propertyValue = propertyValue;
  }

}
