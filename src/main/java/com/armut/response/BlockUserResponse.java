package com.armut.response;

import com.armut.common.ResponseBase;
import com.armut.common.ResponseEnum;

import lombok.Data;

@Data
public class BlockUserResponse extends ResponseBase {
	public BlockUserResponse(String message, String errorCode) {
		super(message, errorCode);
	}

	public BlockUserResponse(ResponseEnum type) {
		super(type);
	}

	public BlockUserResponse() {

	}

}
