package com.armut.request;


import lombok.Data;


@Data
public class SendMessageRequest extends UserRequest {
	
	private String receiverUserName;
	private Message message;


//	public SendMessageRequest(String userName, String receiverUserName, String message, String token) {
//
//		this.message = new MessageBase(message);
//		this.user = new UserRequest(userName,token);
//		this.setReceiverUserName(receiverUserName);
//	}

}
