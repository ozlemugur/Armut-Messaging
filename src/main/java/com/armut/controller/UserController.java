package com.armut.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.armut.common.ActivityEnum;
import com.armut.common.ResponseEnum;
import com.armut.exception.ArmutControllerException;
import com.armut.exception.ArmutServiceException;
import com.armut.request.BlockUserRequest;
import com.armut.request.LoginRequest;
import com.armut.request.SignUpRequest;
import com.armut.request.UserRequest;
import com.armut.response.BlockUserResponse;
import com.armut.response.LoginResponse;
import com.armut.response.SendMessageResponse;
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
	 * Introdcution
	 * 
	 * <dl>
	 * <dt><span class="strong">Heading 1</span></dt><dd>There is a line break.</dd>
	 * <dt><span class="strong">Heading 2</span></dt><dd>There is a line break.</dd>
	 * </dl>
	 *
	 * @param x foo
	 * @return foo
	 * @throws foo
	 */
	@PostMapping(value = "/users/signup")
	public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest)
			throws ArmutServiceException, ArmutControllerException {
		try {
			SignUpResponse response = userService.signUp(signUpRequest);
			if (response.getCode().equals(ResponseEnum.SUCCESS.getCode())) {
				activityLogService.insertActivityLog((UserRequest) signUpRequest, ActivityEnum.SIGNUP, 1);
				return new ResponseEntity<SignUpResponse>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<SignUpResponse>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ArmutControllerException(ex);
		}
	}

	/**
	 * Introdcution
	 * 
	 * <dl>
	 * <dt><span class="strong">Heading 1</span></dt><dd>There is a line break.</dd>
	 * <dt><span class="strong">Heading 2</span></dt><dd>There is a line break.</dd>
	 * </dl>
	 *
	 * @param x foo
	 * @return foo
	 * @throws foo
	 */
	@PostMapping(value = "/users/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest)
			throws ArmutServiceException, ArmutControllerException {
		try {

			ResponseEntity<LoginResponse> validationResponse = validateLoginRequest(loginRequest);
			LoginResponse loginResponsealidationResponse = validationResponse.getBody();
			if (!loginResponsealidationResponse.getCode().equals(ResponseEnum.SUCCESS.getCode()))
				return validationResponse;

			LoginResponse response = userService.login(loginRequest);
			if (response.getCode().equals(ResponseEnum.SUCCESS.getCode())) {
				activityLogService.insertActivityLog((UserRequest) loginRequest, ActivityEnum.LOGIN, 1);
				return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
			} else {
				activityLogService.insertActivityLog((UserRequest) loginRequest, ActivityEnum.LOGIN, 0);
				return new ResponseEntity<LoginResponse>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ArmutControllerException(ex.toString());
		}
	}

	/**
	 * Introdcution
	 * 
	 * <dl>
	 * <dt><span class="strong">Heading 1</span></dt><dd>There is a line break.</dd>
	 * <dt><span class="strong">Heading 2</span></dt><dd>There is a line break.</dd>
	 * </dl>
	 *
	 * @param x foo
	 * @return foo
	 * @throws foo
	 */
	private ResponseEntity<LoginResponse> validateLoginRequest(LoginRequest loginRequest) throws ArmutServiceException {

		LoginResponse checkTokenResponse = userService.checkUser(loginRequest.getUserName());
		if (!checkTokenResponse.getCode().equals(ResponseEnum.SUCCESS.getCode()))
			return new ResponseEntity<LoginResponse>(
					new LoginResponse(checkTokenResponse.getMessage(), checkTokenResponse.getCode()),
					HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<LoginResponse>(new LoginResponse(ResponseEnum.SUCCESS), HttpStatus.OK);

	}

	

	/**
	 * Introdcution
	 * 
	 * <dl>
	 * <dt><span class="strong">Heading 1</span></dt><dd>There is a line break.</dd>
	 * <dt><span class="strong">Heading 2</span></dt><dd>There is a line break.</dd>
	 * </dl>
	 *
	 * @param x foo
	 * @return foo
	 * @throws foo
	 */
	@PostMapping(value = "/users/block")
	public ResponseEntity<BlockUserResponse> blockUser(@Valid @RequestBody BlockUserRequest blockUserRequest)
			throws ArmutServiceException, ArmutControllerException {
		try {
			ResponseEntity<BlockUserResponse> validationResponse = validateBlockUserRequest(blockUserRequest);

			BlockUserResponse blockUserResponseValidationResponse = validationResponse.getBody();
			if (!blockUserResponseValidationResponse.getCode().equals(ResponseEnum.SUCCESS.getCode()))
				return validationResponse;

			BlockUserResponse blockUserResponse = blockedListService.blockUser(blockUserRequest);

			if (blockUserResponse.getCode().equals(ResponseEnum.SUCCESS.getCode())) {
				activityLogService.insertActivityLog(blockUserRequest.getUserName(), ActivityEnum.BLOCKUSER, 1);
				return new ResponseEntity<BlockUserResponse>(blockUserResponse, HttpStatus.OK);
			} else {
				activityLogService.insertActivityLog(blockUserRequest.getUserName(), ActivityEnum.BLOCKUSER, 0);
				return new ResponseEntity<BlockUserResponse>(blockUserResponse, HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			throw new ArmutControllerException(ex);
		}
	}

	/**
	 * Introdcution
	 * 
	 * <dl>
	 * <dt><span class="strong">Heading 1</span></dt><dd>There is a line break.</dd>
	 * <dt><span class="strong">Heading 2</span></dt><dd>There is a line break.</dd>
	 * </dl>
	 *
	 * @param x foo
	 * @return foo
	 * @throws foo
	 */
	private ResponseEntity<BlockUserResponse> validateBlockUserRequest(BlockUserRequest blockUserRequest)
			throws ArmutServiceException {

		LoginResponse checkTokenResponse = userService.checkUserAndToken(blockUserRequest);
		if (!checkTokenResponse.getCode().equals(ResponseEnum.SUCCESS.getCode()))
			return new ResponseEntity<BlockUserResponse>(
					new BlockUserResponse(checkTokenResponse.getMessage(), checkTokenResponse.getCode()),
					HttpStatus.UNAUTHORIZED);

		LoginResponse checkUserResponse = userService.checkUser(blockUserRequest.getToBeBlockedUserName());
		if (!checkUserResponse.getCode().equals(ResponseEnum.SUCCESS.getCode()))
			return new ResponseEntity<BlockUserResponse>(
					new BlockUserResponse(checkUserResponse.getCode(), checkUserResponse.getMessage()),
					HttpStatus.NOT_FOUND);

		return new ResponseEntity<BlockUserResponse>(new BlockUserResponse(ResponseEnum.SUCCESS), HttpStatus.OK);

	}

}
