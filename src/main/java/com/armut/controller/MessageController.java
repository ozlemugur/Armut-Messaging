package com.armut.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.armut.common.ActivityEnum;
import com.armut.common.ActivityStatusEnum;
import com.armut.common.ResponseBase;
import com.armut.common.ResponseEnum;
import com.armut.exception.ArmutControllerException;
import com.armut.exception.ArmutServiceException;
import com.armut.request.GetMessagesRequest;
import com.armut.request.SendMessageRequest;
import com.armut.request.UserRequest;
import com.armut.response.GetMessagesResponse;
import com.armut.response.LoginResponse;
import com.armut.response.SendMessageResponse;
import com.armut.service.ActivityLogService;
import com.armut.service.MessageService;
import com.armut.service.UserService;

@RestController
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private ActivityLogService activityLogService;

	@Autowired
	private UserService userService;

	/**
	 * <dl>
	 * <dt><span class="strong">get messages function/span></dt>
	 * 
	 * <dd>User name, token are mandatory.</dd>
	 * </dl>
	 * 
	 * @param GetMessagesRequest getMessagesRequest
	 * @return ResponseEntity {@literal <GetMessagesResponse>}
	 * @throws throws ArmutServiceException, ArmutControllerException
	 */
	@GetMapping(value = "/messages/getmessages")
	public ResponseEntity<GetMessagesResponse> getMessages(@Valid @RequestBody GetMessagesRequest getMessagesRequest)
			throws ArmutServiceException, ArmutControllerException {
		try {

			LoginResponse validateTokenResponse = userService.validateToken(getMessagesRequest);
			if (!validateTokenResponse.equals(ResponseEnum.SUCCESS)) {
				activityLogService.insertActivityLog(getMessagesRequest.getUserName(), ActivityEnum.SENDMESSAGE,
						ActivityStatusEnum.FAILED, validateTokenResponse);
				return new ResponseEntity<GetMessagesResponse>(
						new GetMessagesResponse((ResponseBase) validateTokenResponse), HttpStatus.UNAUTHORIZED);
			}

			if (validateTokenResponse.equals(ResponseEnum.SUCCESS)) {
				getMessagesRequest.setUserId(validateTokenResponse.getUserId());

				GetMessagesResponse getMessagesResponse = messageService.getMessages(getMessagesRequest);
				if (getMessagesResponse.equals(ResponseEnum.SUCCESS)) {

					activityLogService.insertActivityLog(getMessagesRequest.getUserName(), ActivityEnum.GETMESSAGES,
							ActivityStatusEnum.SUCCESS, getMessagesResponse);
					return new ResponseEntity<GetMessagesResponse>(getMessagesResponse, HttpStatus.OK);
				} else {
					activityLogService.insertActivityLog(getMessagesRequest.getUserName(), ActivityEnum.GETMESSAGES,
							ActivityStatusEnum.FAILED, getMessagesResponse);
					return new ResponseEntity<GetMessagesResponse>(getMessagesResponse, HttpStatus.NOT_FOUND);
				}
			} else {
				activityLogService.insertActivityLog((UserRequest) getMessagesRequest, ActivityEnum.LOGIN,
						ActivityStatusEnum.FAILED, validateTokenResponse);
				return new ResponseEntity<GetMessagesResponse>(
						new GetMessagesResponse((ResponseBase) validateTokenResponse), HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			throw new ArmutControllerException(ex.toString());
		}
	}

	/**
	 * <dl>
	 * <dt><span class="strong">send message function/span></dt>
	 * 
	 * <dd>User name, token, receiverUserName are mandatory.</dd>
	 * </dl>
	 * 
	 * @param SendMessageRequest sendMessageRequest
	 * @return ResponseEntity {@literal <SendMessageResponse>}
	 * @throws throws ArmutServiceException, ArmutControllerException
	 */
	@PostMapping(value = "/messages/sendmessage")
	public ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequest sendMessageRequest)
			throws ArmutServiceException, ArmutControllerException {
		try {
			ResponseBase validateTokenResponse = userService.validateToken(sendMessageRequest);
			if (!validateTokenResponse.equals(ResponseEnum.SUCCESS)) {
				activityLogService.insertActivityLog(sendMessageRequest.getUserName(), ActivityEnum.SENDMESSAGE,
						ActivityStatusEnum.FAILED, validateTokenResponse);
				return new ResponseEntity<SendMessageResponse>(
						new SendMessageResponse(validateTokenResponse), HttpStatus.UNAUTHORIZED);
			}

			SendMessageResponse sendMessageResponse = messageService.sendMessage(sendMessageRequest);

			if (sendMessageResponse.equals(ResponseEnum.SUCCESS)) {
				activityLogService.insertActivityLog(sendMessageRequest.getUserName(), ActivityEnum.SENDMESSAGE,
						ActivityStatusEnum.SUCCESS, sendMessageResponse);
				return new ResponseEntity<SendMessageResponse>(sendMessageResponse, HttpStatus.OK);
			} else {
				activityLogService.insertActivityLog(sendMessageRequest.getUserName(), ActivityEnum.SENDMESSAGE,
						ActivityStatusEnum.FAILED, sendMessageResponse);
				return new ResponseEntity<SendMessageResponse>(sendMessageResponse, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ArmutControllerException(ex.toString());
		}

	}

}
