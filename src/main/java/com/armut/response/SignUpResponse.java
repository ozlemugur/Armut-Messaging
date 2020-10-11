package com.armut.response;

import com.armut.common.ResponseBase;
import com.armut.common.ResponseEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(value = { "userId" }, allowGetters = false)
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
