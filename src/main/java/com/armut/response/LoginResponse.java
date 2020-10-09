package com.armut.response;

import com.armut.common.ResponseBase;
import com.armut.common.ResponseEnum;

import lombok.Data;

@Data
public class LoginResponse extends ResponseBase {

	private String token;

	private Long userId;

	public LoginResponse() {
	}
	
	public LoginResponse(String message, String code) {
		super(message, code);
	}

	public LoginResponse(String token, ResponseEnum type) {
		super(type);
		this.token = token;
	}

	public LoginResponse(Long userId, String token, ResponseEnum type) {
		super(type);
		this.token = token;
		this.userId = userId;
	}

	public LoginResponse(ResponseEnum type) {
		super(type);
	}
	
	public LoginResponse(Long userId, ResponseEnum type) {
		super(type);
		this.userId = userId;
	}
}
