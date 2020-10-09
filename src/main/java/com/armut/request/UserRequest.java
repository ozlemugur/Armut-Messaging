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

//	@Autowired
//	public UserService userService;
//
//	public ResponseBase validate() throws ArmutServiceException {
//		ResponseBase checkTokenResponse = userService.checkUser(this.getUserName());
//		if (!checkTokenResponse.getCode().equals(ResponseEnum.SUCCESS.getCode()))
//			return new LoginResponse(checkTokenResponse.getMessage(), checkTokenResponse.getCode());
//		return new ResponseBase(ResponseEnum.SUCCESS);
//
//	}
//
//	public LoginResponse validateToken() throws ArmutServiceException {
//		LoginResponse checkTokenResponse = userService.validateToken(this);
//		if (!checkTokenResponse.equals(ResponseEnum.SUCCESS))
//			return new LoginResponse(checkTokenResponse.getMessage(), checkTokenResponse.getCode());
//		return new LoginResponse(ResponseEnum.SUCCESS);
//	}

}
