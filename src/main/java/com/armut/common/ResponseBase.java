package com.armut.common;

import lombok.Data;

@Data
public  class ResponseBase extends Object {

	private String message;
	private String code;

	public ResponseBase() {

	}

	public ResponseBase(String message, String code) {
		this.code = code;
		this.message = message;
	}

	public ResponseBase(ResponseEnum type) {
		this.code = type.getCode();
		this.message = type.getMessage();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ResponseBase)) {
			return false;
		}

		ResponseBase response = (ResponseBase) o;
		return this.code.equals(response.getCode());
	}
	
	public boolean equals (ResponseEnum responseEnum) {
		return this.getCode().equals(responseEnum.getCode());
	}

}
