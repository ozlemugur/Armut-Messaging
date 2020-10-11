package com.armut.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.armut.common.ResponseEnum;
import com.armut.common.ServiceBase;
import com.armut.common.Utils;
import com.armut.exception.ArmutServiceException;
import com.armut.model.UserEntity;
import com.armut.repository.UserRepository;
import com.armut.request.LoginRequest;
import com.armut.request.SignUpRequest;
import com.armut.request.UserRequest;
import com.armut.response.LoginResponse;
import com.armut.response.SignUpResponse;

@Service
public class UserService extends ServiceBase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Utils utils;

	/**
	 * 
	 * <dl>
	 * <dt><span class="strong">public SignUpResponse signUp(SignUpRequest signUpRequest)</span></dt>
	 * <dd> signup operation</dd>
	 * </dl>
	 *
	 * @param SignUpRequest signUpRequest
	 * @return SignUpResponse
	 * @throws ArmutServiceException
	 */
	public SignUpResponse signUp(SignUpRequest signUpRequest) throws ArmutServiceException {
		try {
			if (!(userRepository.findByUserName(signUpRequest.getUserName()) == null))
				return new SignUpResponse(ResponseEnum.USERNAME_ALREADY_EXISTS);
			UserEntity user = new UserEntity();
			BeanUtils.copyProperties(signUpRequest, user);
			user.setPassword(utils.hashPassword(signUpRequest.getPassword()));
			user = userRepository.save(user);

			if (user != null)
				return new SignUpResponse(ResponseEnum.SUCCESS);
			else
				return new SignUpResponse(ResponseEnum.SYSTEM_EXCEPTION);
		} catch (Exception ex) {
			throw new ArmutServiceException(signUpRequest.getUserName(), ex);
		}
	}

	/**
	 * 
	 * <dl>
	 * <dt><span class="strong">public LoginResponse login(LoginRequest loginRequest) throws ArmutServiceException </span></dt>
	 * <dd>There is a line break.</dd>
	 * <dt><span class="strong">login operation/span></dt>
	 * <dd>There is a line break.</dd>
	 * </dl>
	 *
	 * @param LoginRequest loginRequest
	 * @return LoginResponse
	 * @throws ArmutServiceException
	 */
	public LoginResponse login(LoginRequest loginRequest) throws ArmutServiceException {
		try {
			UserEntity user = userRepository.findByUserName(loginRequest.getUserName());
			if (user == null)
				return new LoginResponse(ResponseEnum.CHECK_YOUR_DATA);

			if (!checkUserPassword(user))
				return new LoginResponse(ResponseEnum.AUTHENTICATION_ERROR);

			user.setToken(utils.generateToken());
			user = userRepository.save(user);
			if (user != null)
				return new LoginResponse(user.getToken(), ResponseEnum.SUCCESS);
			else
				return new LoginResponse(ResponseEnum.SYSTEM_EXCEPTION);
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

	/**
	 * 
	 * <dl>
	 * <dt><span class="strong">public boolean checkUserPassword(UserEntity user) throws ArmutServiceException </span></dt>
	 * <dd>it is used for checking user's password</dd>
	 * </dl>
	 *
	 * @param serEntity user
	 * @return boolean
	 * @throws ArmutServiceException
	 */
	public boolean checkUserPassword(UserEntity user) throws ArmutServiceException {
		try {
			Optional<UserEntity> userDb = userRepository.findById(user.getId());
			return userDb.get().getPassword().equals(user.getPassword());
		} catch (Exception ex) {
			throw new ArmutServiceException(user.getId(), ex);
		}
	}

	/**
	 * 
	 * <dl>
	 * <dt><span class="strong">public LoginResponse validateToken(UserRequest userRequest) throws ArmutServiceException </span></dt>
	 * <dd>Validate the user token which was given on the login process</dd> 
	 * </dl>
	 *
	 * @param UserRequest userRequest
	 * @return LoginResponse
	 * @throws  ArmutServiceException 
	 */
	public LoginResponse validateToken(UserRequest userRequest) throws ArmutServiceException {
		try {
			// is randomization of token enough for me, UUID and username my primary key.
			UserEntity user = userRepository.findByUserName(userRequest.getUserName());
			if (user == null)
				return new LoginResponse(ResponseEnum.CHECK_YOUR_DATA);
			if (user.getToken() != null ? !user.getToken().equals(userRequest.getToken()) : false) {
				return new LoginResponse(ResponseEnum.TOKEN_EXPIRED);
			}
			return new LoginResponse(user.getId(), user.getToken(), ResponseEnum.SUCCESS);
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

	/**
	 * Introdcution
	 * 
	 * <dl>
	 * <dt><span class="strong">LoginResponse checkUser(String userName) throws ArmutServiceException </span></dt>
	 * <dd>it is used for checking </dd>
	 * <dt><span class="strong">Heading 2</span></dt>
	 * <dd>There is a line break.</dd>
	 * </dl>
	 *
	 * @param x foo
	 * @return foo
	 * @throws foo
	 */
	public LoginResponse checkUser(String userName) throws ArmutServiceException {
		try {
			UserEntity user = userRepository.findByUserName(userName);
			if (user == null)
				return new LoginResponse(ResponseEnum.CHECK_YOUR_DATA);

			return new LoginResponse(user.getId(), ResponseEnum.SUCCESS);
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

	/**
	 * Introdcution
	 * 
	 * <dl>
	 * <dt><span class="strong">Heading 1</span></dt>
	 * <dd>There is a line break.</dd>
	 * <dt><span class="strong">Heading 2</span></dt>
	 * <dd>There is a line break.</dd>
	 * </dl>
	 *
	 * @param x foo
	 * @return foo
	 * @throws foo
	 */
	public String getUserNameById(Long userId) throws ArmutServiceException {
		try {
			String userName = null;
			Optional<UserEntity> user = userRepository.findById(userId);
			if (user.get() != null)
				userName = user.get().getUserName();
			return userName;
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

	public UserEntity getUserByUserName(String userName) throws ArmutServiceException {
		try {
			return userRepository.findByUserName(userName);
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

}
