package com.armut.response;

import java.util.List;

import com.armut.common.ResponseBase;
import com.armut.common.ResponseEnum;
import com.armut.request.Message;

import lombok.Data;

@Data
public class GetMessagesResponse extends ResponseBase {

	public GetMessagesResponse() {

	}

	public GetMessagesResponse(String message, String errorCode) {
		super(message, errorCode);
	}

	public GetMessagesResponse(ResponseEnum type) {
		super(type);
	}

	private List<Message> messages;

}
