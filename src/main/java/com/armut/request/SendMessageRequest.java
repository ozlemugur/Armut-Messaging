package com.armut.request;


import lombok.Data;


@Data
public class SendMessageRequest extends UserRequest {
	
	private String receiverUserName;
	private Message message;


}
