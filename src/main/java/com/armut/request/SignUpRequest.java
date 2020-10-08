package com.armut.request;

import lombok.Data;

@Data
public class SignUpRequest extends UserRequest {

	private String password;

//	public SignUpRequest(String userName, String password, String token) {
//		this.setUserName(userName);
//		
//	}

}
