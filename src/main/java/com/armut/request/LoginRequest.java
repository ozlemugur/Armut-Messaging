package com.armut.request;

import lombok.Data;

@Data
public class LoginRequest extends UserRequest {
	
	
	public LoginRequest(String userName, String password) {
		this.setUserName(userName);
		this.setPassword(password); 
	}
}
