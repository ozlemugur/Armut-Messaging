package com.armut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.armut.common.ResponseEnum;
import com.armut.common.ServiceBase;
import com.armut.exception.ArmutServiceException;
import com.armut.model.BlockedListEntity;
import com.armut.repository.BlockedListRepository;
import com.armut.repository.UserRepository;
import com.armut.request.BlockUserRequest;
import com.armut.response.BlockUserResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BlockedListService extends ServiceBase {

	@Autowired
	private BlockedListRepository blockedListRepository;

	@Autowired
	private UserRepository userRepository;
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BlockUserResponse blockUser(BlockUserRequest blockUserRequest) throws ArmutServiceException {
		BlockedListEntity blockedUser = new BlockedListEntity();
		try {

			Long userId = userRepository.findByUserName(blockUserRequest.getUserName()).getId();
			Long toBeBlockedUserId = userRepository.findByUserName(blockUserRequest.getToBeBlockedUserName()).getId();

			if (isUserBlockedByProcessUser(userId, toBeBlockedUserId))
				return new BlockUserResponse(ResponseEnum.BLOCKED_USER_ALREADY);

			blockedUser.setBlockedId(toBeBlockedUserId);
			blockedUser.setUserId(userId);
			blockedUser = blockedListRepository.save(blockedUser);

			if (blockedUser != null)
				return new BlockUserResponse(ResponseEnum.SUCCESS);
			else
				return new BlockUserResponse(ResponseEnum.SYSTEM_EXCEPTION);

		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
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
	public boolean isUserBlockedByProcessUser(long senderUserId, Long receiverUserId) {

		BlockedListEntity blockedList = blockedListRepository.findByUserIdAndBlockedId(receiverUserId, senderUserId);
		return blockedList == null ? false : true;

	}

}
