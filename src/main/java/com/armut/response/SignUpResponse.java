package com.armut.response;

import com.armut.common.ResponseBase;
import com.armut.common.ResponseEnum;

import lombok.Data;

@Data
public class SignUpResponse extends ResponseBase {

	public SignUpResponse() {
	}

	public SignUpResponse(String message, String errorCode) {
		super(message, errorCode);
	}

	public SignUpResponse(ResponseEnum type) {
		super(type);
	}

}
