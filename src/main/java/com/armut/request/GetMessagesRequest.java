package com.armut.request;

import lombok.Data;

@Data
public class GetMessagesRequest extends UserRequest {

	private String userName;
	private String token;
	private Long userId;



}
