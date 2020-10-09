package com.armut.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.armut.common.ResponseBase;
import com.armut.common.ResponseEnum;
import com.armut.exception.ArmutServiceException;
import com.armut.response.LoginResponse;
import com.armut.service.UserService;

public class ControllerRequest {
	@Autowired
	public UserService userService;

	public ResponseBase validate() throws ArmutServiceException {
		ResponseBase checkTokenResponse = userService.checkUser("devil");
		if (!checkTokenResponse.getCode().equals(ResponseEnum.SUCCESS.getCode()))
			return new LoginResponse(checkTokenResponse.getMessage(), checkTokenResponse.getCode());
		return new ResponseBase(ResponseEnum.SUCCESS);

	}

}
