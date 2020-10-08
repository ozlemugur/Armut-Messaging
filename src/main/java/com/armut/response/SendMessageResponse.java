package com.armut.response;

import com.armut.common.ResponseBase;
import com.armut.common.ResponseEnum;

import lombok.Data;

@Data
public class SendMessageResponse extends ResponseBase {

	public SendMessageResponse() {

	}

	public SendMessageResponse(String message, String errorCode) {
		super(message, errorCode);
	}

	public SendMessageResponse(ResponseEnum type) {
		super(type);
	}

}
