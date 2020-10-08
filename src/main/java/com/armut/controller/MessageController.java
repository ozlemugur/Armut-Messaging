package com.armut.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.armut.common.ActivityEnum;
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
	@GetMapping(value = "/messages/getmessages")
	public ResponseEntity<GetMessagesResponse> getMessages(@Valid @RequestBody GetMessagesRequest getMessagesRequest)
			throws ArmutServiceException, ArmutControllerException {
		try {
			LoginResponse checkTokenResponse = userService.checkUserAndToken(getMessagesRequest);
			if (checkTokenResponse.getCode().equals(ResponseEnum.SUCCESS.getCode())) {
				getMessagesRequest.setUserId(checkTokenResponse.getUserId());
				GetMessagesResponse getMessagesResponse = messageService.getMessages(getMessagesRequest);
				if (getMessagesResponse.getCode().equals(ResponseEnum.SUCCESS.getCode())) {
					activityLogService.insertActivityLog(getMessagesRequest.getUserName(), ActivityEnum.GETMESSAGES, 1);
					return new ResponseEntity<GetMessagesResponse>(getMessagesResponse, HttpStatus.OK);
				} else {
					return new ResponseEntity<GetMessagesResponse>(getMessagesResponse, HttpStatus.NOT_FOUND);
				}
			} else {
				activityLogService.insertActivityLog((UserRequest) getMessagesRequest, ActivityEnum.LOGIN, 0);
				return new ResponseEntity<GetMessagesResponse>(
						new GetMessagesResponse(checkTokenResponse.getMessage(), checkTokenResponse.getCode()),
						HttpStatus.UNAUTHORIZED);
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
	@PostMapping(value = "/messages/sendmessage")
	public ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequest sendMessageRequest)
			throws ArmutServiceException, ArmutControllerException {
		try {
			ResponseEntity<SendMessageResponse> validationResponse = validateSendMessageRequest(sendMessageRequest);
			SendMessageResponse sendMessageValidationResponse = validationResponse.getBody();
			if (!sendMessageValidationResponse.getCode().equals(ResponseEnum.SUCCESS.getCode()))
				return validationResponse;

			SendMessageResponse sendMessageResponse = messageService.sendMessage(sendMessageRequest);

			if (sendMessageResponse.getCode().equals(ResponseEnum.SUCCESS.getCode())) {
				activityLogService.insertActivityLog(sendMessageRequest.getUserName(), ActivityEnum.SENDMESSAGE, 1);
				return new ResponseEntity<SendMessageResponse>(sendMessageResponse, HttpStatus.OK);
			} else {
				activityLogService.insertActivityLog(sendMessageRequest.getUserName(), ActivityEnum.SENDMESSAGE, 0);
				return new ResponseEntity<SendMessageResponse>(sendMessageResponse, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ArmutControllerException(ex.toString());
		}

	}

	private ResponseEntity<SendMessageResponse> validateSendMessageRequest(SendMessageRequest sendMessageRequest) throws ArmutServiceException {

		LoginResponse checkTokenResponse = userService.checkUserAndToken(sendMessageRequest);
		if (checkTokenResponse.getCode().equals(ResponseEnum.SUCCESS.getCode())) {
			LoginResponse checkUserResponse = userService.checkUser(sendMessageRequest.getUserName());
			if (checkUserResponse.getCode().equals(ResponseEnum.SUCCESS.getCode())) {
				return new ResponseEntity<SendMessageResponse>(new SendMessageResponse(ResponseEnum.SUCCESS),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<SendMessageResponse>(
						new SendMessageResponse(checkUserResponse.getCode(), checkUserResponse.getMessage()),
						HttpStatus.NOT_FOUND);
			}

		} else {
			return new ResponseEntity<SendMessageResponse>(
					new SendMessageResponse(checkTokenResponse.getMessage(), checkTokenResponse.getCode()),
					HttpStatus.UNAUTHORIZED);
		}

	}
//	@PostMapping(value = "/messages/{userName}/{receiverUserName}")
//	public ResponseEntity<Message> sendMessage(@RequestBody Message message,
//			@PathVariable("userName") String senderUserName, @PathVariable("receiverUserName") String receiverUserName)
//			throws ServiceException {
//
//		if (userService.checkUserNameExistance(senderUserName)
//				&& userService.checkUserNameExistance(receiverUserName)) {
//			Long senderUserId = userService.getUserByUserName(senderUserName).getId();
//			Long receiverUserId = userService.getUserByUserName(receiverUserName).getId();
//			if (!blockedListService.isUserBlockedByProcessUser(senderUserId, receiverUserId)) {
//				message.setSenderId(senderUserId);
//				message.setReceiverId(receiverUserId);
//				return new ResponseEntity<Message>(messageService.sendMessage(message), HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Message>(new Message(), HttpStatus.UNAUTHORIZED);
//			}
//		} else {
//			return new ResponseEntity<Message>(new Message(), HttpStatus.NOT_FOUND);
//		}
//	}
//	
//	
//	@GetMapping(value = "/messages/{userName}")
//	public ResponseEntity<List<Message>> getMessages(@Valid @PathVariable("userName") String userName)
//			throws ServiceException, MessageNotFoundException, CheckInfoException {
//
//		List<Message> messages = messageService.getMessages(userName);
//		return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
//
////		if (userService.checkUserNameExistance(userName)) {
////			User userDbForm = ç.getUserByUserName(userName);
////			List<Message> messages = messageService.getMessages(userDbForm.getId());
////			if (messages.size() > 0) {
////				return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
////			} else {
////				return new ResponseEntity<List<Message>>(new ArrayList<Message>(), HttpStatus.NOT_FOUND);
////			}
////		} else {
////			return new ResponseEntity<List<Message>>(new ArrayList<Message>(), HttpStatus.UNAUTHORIZED);
////		}
//
//	}

}
