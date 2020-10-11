package com.armut.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(value = { "token" }, allowGetters = false)
@Data
public class LoginRequest extends UserRequest {
	
	
	public LoginRequest(String userName, String password) {
		this.setUserName(userName);
		this.setPassword(password); 
	}
}
