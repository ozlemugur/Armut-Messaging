package com.armut.common;


public enum ResponseEnum {

	SUCCESS("0000", "success."), SYSTEM_EXCEPTION("0001", "System Exception"),
	AUTHENTICATION_ERROR("0002", "Authentication error"),
	BLOCKED_USER("0003", "You can not send message to this user."),
	BLOCKED_USER_ALREADY("0004", "You had blocked the user."),
	TOKEN_EXPIRED("0005", "try to login again."),
	CHECK_YOUR_DATA("0006", "Check your login data."),
	USERNAME_ALREADY_EXISTS("0006", "(username already exists.");
	
	;

	private String code;
	private String message;

	private ResponseEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
