package com.armut.request;

import lombok.Data;

@Data
public class GetMessagesRequest extends UserRequest {

	private String userName;
	private String token;
	private Long userId;

//	private MessageBase message;

//	public GetMessagesRequest(String userName, String token) {
//		this.setUserName(userName);
//		this.setToken(token);
//	}

}
