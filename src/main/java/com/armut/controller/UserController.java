package com.armut.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.armut.common.ActivityEnum;
import com.armut.common.ActivityStatusEnum;
import com.armut.common.ResponseBase;
import com.armut.common.ResponseEnum;
import com.armut.exception.ArmutControllerException;
import com.armut.exception.ArmutServiceException;
import com.armut.request.BlockUserRequest;
import com.armut.request.LoginRequest;
import com.armut.request.SignUpRequest;
import com.armut.request.UserRequest;
import com.armut.response.BlockUserResponse;
import com.armut.response.LoginResponse;
import com.armut.response.SignUpResponse;
import com.armut.service.ActivityLogService;
import com.armut.service.BlockedListService;
import com.armut.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private BlockedListService blockedListService;

	/**
	 * <dl>
	 * <dt><span class="strong">sign up function/span></dt>
	 * 
	 * <dd>User name and password are mandatory</dd>
	 * </dl>
	 * 
	 * @param SignUpRequest signUpRequest
	 * @return ResponseEntity {@literal <SignUpResponse>}
	 * @throws throws ArmutServiceException, ArmutControllerException
	 */
	@PostMapping(value = "/users/signup")
	public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest)
			throws ArmutServiceException, ArmutControllerException {
		try {
			SignUpResponse response = userService.signUp(signUpRequest);
			if (response.equals(ResponseEnum.SUCCESS)) {
				activityLogService.insertActivityLog((UserRequest) signUpRequest, ActivityEnum.SIGNUP,
						ActivityStatusEnum.SUCCESS, response);
				return new ResponseEntity<SignUpResponse>(response, HttpStatus.OK);
			} else {
				activityLogService.insertActivityLog((UserRequest) signUpRequest, ActivityEnum.SIGNUP,
						ActivityStatusEnum.FAILED, response);
				return new ResponseEntity<SignUpResponse>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ArmutControllerException(ex);
		}
	}

	/**
	 * <dl>
	 * <dt><span class="strong">login function/span></dt>
	 * 
	 * <dd>User name and password are mandatory.</dd>
	 * </dl>
	 * 
	 * @param
	 * @return ResponseEntity {@literal <SignUpResponse>}
	 * @throws throws ArmutServiceException, ArmutControllerException
	 */
	@PostMapping(value = "/users/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest)
			throws ArmutServiceException, ArmutControllerException {
		try {
			LoginResponse response = userService.login(loginRequest);
			if (response.equals(ResponseEnum.SUCCESS)) {
				activityLogService.insertActivityLog((UserRequest) loginRequest, ActivityEnum.LOGIN,
						ActivityStatusEnum.SUCCESS, response);
				return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
			} else {
				activityLogService.insertActivityLog((UserRequest) loginRequest, ActivityEnum.LOGIN,
						ActivityStatusEnum.FAILED, response);
				if (response.equals(ResponseEnum.CHECK_YOUR_DATA))
					return new ResponseEntity<LoginResponse>(response, HttpStatus.UNAUTHORIZED);
				else
					return new ResponseEntity<LoginResponse>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ArmutControllerException(ex.toString());
		}
	}

	/**
	 * <dl>
	 * <dt><span class="strong">block user function/span></dt>
	 * 
	 * <dd>User name, token, toBeBlockedUserName are mandatory.</dd>
	 * </dl>
	 * 
	 * @param
	 * @return ResponseEntity {@literal <SignUpResponse>}
	 * @throws throws ArmutServiceException, ArmutControllerException
	 */
	@PostMapping(value = "/users/block")
	public ResponseEntity<BlockUserResponse> blockUser(@Valid @RequestBody BlockUserRequest blockUserRequest)
			throws ArmutServiceException, ArmutControllerException {
		try {

			ResponseBase validateTokenResponse = userService.validateToken(blockUserRequest);
			if (!validateTokenResponse.equals(ResponseEnum.SUCCESS)) {
				activityLogService.insertActivityLog(blockUserRequest.getUserName(), ActivityEnum.BLOCKUSER,
						ActivityStatusEnum.FAILED, validateTokenResponse);
				return new ResponseEntity<BlockUserResponse>((BlockUserResponse) validateTokenResponse,
						HttpStatus.UNAUTHORIZED);
			}

			ResponseBase checkToBeBlockedUserResponse = userService
					.checkUser(blockUserRequest.getToBeBlockedUserName());
			if (!checkToBeBlockedUserResponse.equals(ResponseEnum.SUCCESS)) {
				activityLogService.insertActivityLog(blockUserRequest.getUserName(), ActivityEnum.BLOCKUSER,
						ActivityStatusEnum.FAILED, checkToBeBlockedUserResponse);
				return new ResponseEntity<BlockUserResponse>((BlockUserResponse) checkToBeBlockedUserResponse,
						HttpStatus.UNAUTHORIZED);
			}

			BlockUserResponse blockUserResponse = blockedListService.blockUser(blockUserRequest);

			if (blockUserResponse.equals(ResponseEnum.SUCCESS)) {
				activityLogService.insertActivityLog(blockUserRequest.getUserName(), ActivityEnum.BLOCKUSER,
						ActivityStatusEnum.SUCCESS, blockUserResponse);
				return new ResponseEntity<BlockUserResponse>(blockUserResponse, HttpStatus.OK);
			} else {
				activityLogService.insertActivityLog(blockUserRequest.getUserName(), ActivityEnum.BLOCKUSER,
						ActivityStatusEnum.FAILED, blockUserResponse);
				return new ResponseEntity<BlockUserResponse>(blockUserResponse, HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			throw new ArmutControllerException(ex);
		}
	}

}
