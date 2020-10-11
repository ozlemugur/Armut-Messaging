package com.armut.request;

import com.armut.common.RequestBase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(value = { "userId" }, allowGetters = false)
@Data
public abstract class UserRequest extends RequestBase {

	private String userName;
	private String token;
	private String password;
	private Long userId;

}
