package com.armut.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.armut.common.ResponseBase;
import com.armut.common.ResponseEnum;
import com.armut.common.ServiceBase;
import com.armut.exception.ArmutServiceException;
import com.armut.model.MessageEntity;
import com.armut.repository.MessageRepository;
import com.armut.request.GetMessagesRequest;
import com.armut.request.Message;
import com.armut.request.SendMessageRequest;
import com.armut.response.GetMessagesResponse;
import com.armut.response.LoginResponse;
import com.armut.response.SendMessageResponse;

@Service
public class MessageService extends ServiceBase {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private BlockedListService blockedListService;

	/**
	 * 
	 * <dl>
	 * <dt><span class="strong"></span>getMessages</dt>
	 * <dd>it is used for getting user's all messages</dd>
	 * <dt></dt>
	 * </dl>
	 *
	 * @param GetMessagesRequest getMessagesRequest
	 * @return GetMessagesResponse
	 * @throws ArmutServiceException
	 */
	public GetMessagesResponse getMessages(GetMessagesRequest getMessagesRequest) throws ArmutServiceException {
		try {
			List<MessageEntity> messages = new ArrayList<MessageEntity>();
			List<MessageEntity> receiverMessageList = messageRepository
					.findByReceiverId(getMessagesRequest.getUserId());
			List<MessageEntity> senderMessagelist = messageRepository.findBySenderId(getMessagesRequest.getUserId());

			messages.addAll(receiverMessageList);
			messages.addAll(senderMessagelist);
			GetMessagesResponse getMessagesResponse = new GetMessagesResponse(ResponseEnum.SUCCESS);
			getMessagesResponse.setMessages(copyMessageListToMessageRequestList(messages));

			return getMessagesResponse;
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

	/**
	 * 
	 * <dl>
	 * <dt><span class="strong">sendMessage</span></dt>
	 * <dd>it is used for sending a message one user to another user</dd>
	 * </dl>
	 *
	 * @param SendMessageRequest sendMessageRequest
	 * @return SendMessageResponse
	 * @throws ArmutServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SendMessageResponse sendMessage(SendMessageRequest sendMessageRequest) throws ArmutServiceException {
		try {
			LoginResponse receiverStatus = userService.checkUser(sendMessageRequest.getReceiverUserName());
			if (!receiverStatus.equals(ResponseEnum.SUCCESS))
				return  (SendMessageResponse)(ResponseBase)receiverStatus;
			Long senderUserId = userService.checkUser(sendMessageRequest.getUserName()).getUserId();

			if (blockedListService.isUserBlockedByProcessUser(senderUserId, receiverStatus.getUserId()))
				return new SendMessageResponse(ResponseEnum.BLOCKED_USER);

			MessageEntity message = new MessageEntity();
			BeanUtils.copyProperties(sendMessageRequest.getMessage(), message);
			message.setSenderId(senderUserId);
			message.setReceiverId(receiverStatus.getUserId());
			messageRepository.save(message);

			return new SendMessageResponse(ResponseEnum.SUCCESS);
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

	/**
	 * 
	 * <dl>
	 * <dt><span class="strong">copyMessageListToMessageRequestList</span></dt>
	 * <dd>it is used for copying messages, MessageEntity to Message</dd>
	 * </dl>
	 *
	 * @param List{@literal<MessageEntity>} entityList)
	 * @return List{@literal<Message>}
	 * @throws ArmutServiceException }
	 */
	private List<Message> copyMessageListToMessageRequestList(List<MessageEntity> entityList)
			throws ArmutServiceException {
		List<Message> messageList = new ArrayList<Message>();
		for (MessageEntity messageEntity : entityList) {
			Message message = new Message(userService.getUserNameById(messageEntity.getSenderId()),
					userService.getUserNameById(messageEntity.getReceiverId()), messageEntity.getContent());
			messageList.add(message);
		}
		return messageList;

	}

	private void notifyClient() {

	}

}