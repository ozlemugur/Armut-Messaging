package com.armut.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(value = { "token" }, allowGetters = false)
@Data
public class SignUpRequest extends UserRequest {

	private String password;


}
