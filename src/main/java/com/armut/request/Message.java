package com.armut.request;

import lombok.Data;

@Data
public class Message extends Object {
	
	private String senderName;
	private String receiverName;
	private String content;
	
	public Message(String senderName, String receiverName, String content) {
		this.setContent(content);
		this.setReceiverName(receiverName);
		this.setSenderName(senderName);
	}

}
