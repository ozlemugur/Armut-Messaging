package com.armut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.armut.common.ActivityEnum;
import com.armut.common.ActivityStatusEnum;
import com.armut.common.ResponseBase;
import com.armut.common.ServiceBase;
import com.armut.exception.ArmutServiceException;
import com.armut.model.ActivityLogEntity;
import com.armut.model.UserEntity;
import com.armut.repository.ActivityLogRepository;
import com.armut.repository.UserRepository;
import com.armut.request.UserRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActivityLogService extends ServiceBase {

	@Autowired
	private ActivityLogRepository activityLogRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * <dl>
	 * <dt><span class="strong">insertActivityLog by UserEntity</span></dt>
	 * <dd></dd>
	 * <dd>it inserts the related activity logs</dd>
	 * </dl>
	 *
	 * @param UserEntity user, ActivityEnum activityName, ActivityStatusEnum
	 *                   activityStatus, ResponseBase response
	 * @return void
	 * @throws ArmutServiceException
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void insertActivityLog(UserEntity user, ActivityEnum activityName, ActivityStatusEnum activityStatus,
			ResponseBase response) throws ArmutServiceException {
		try {
			ActivityLogEntity activityLog = new ActivityLogEntity();
			activityLog.setActivityName(activityName.toString());
			activityLog.setActivityDetail(response.getMessage());
			if (user != null)
			activityLog.setUserId(user.getId());
			activityLog.setActivityStatus(activityStatus.getValue());
			activityLogRepository.save(activityLog);
			log.error("ARMUT ACTIVITY LOG [INFO]  ********** " + response.getMessage());
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

	/**
	 * <dl>
	 * <dt><span class="strong">insertActivityLog by userName</span></dt>
	 * <dd></dd>
	 * <dd>it finds user id by user name and inserts the related activity logs</dd>
	 * </dl>
	 *
	 * @param String userName, ActivityEnum activityName, ActivityStatusEnum
	 *               activityStatus, ResponseBase response
	 * @return void
	 * @throws ArmutServiceException
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void insertActivityLog(String userName, ActivityEnum activityName, ActivityStatusEnum activityStatus,
			ResponseBase response) throws ArmutServiceException {
		try {
			UserEntity user = userRepository.findByUserName(userName);
			ActivityLogEntity activityLog = new ActivityLogEntity();
			activityLog.setActivityName(activityName.toString());
			activityLog.setActivityDetail(response.getMessage());
			if (user != null)
				activityLog.setUserId(user.getId());
			activityLog.setActivityStatus(activityStatus.getValue());
			log.error("ARMUT ACTIVITY LOG [INFO]  ********** " + response.getMessage());
			
			activityLogRepository.save(activityLog);
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

	/**
	 * <dl>
	 * <dt><span class="strong">insertActivityLog by UserRequest</span></dt>
	 * <dd></dd>
	 * <dd>it inserts the related activity logs</dd>
	 * </dl>
	 *
	 * @param UserRequest userRequest, ActivityEnum activityName, ActivityStatusEnum
	 *                    activityStatus, ResponseBase response
	 * @return void
	 * @throws ArmutServiceException
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void insertActivityLog(UserRequest userRequest, ActivityEnum activityName, ActivityStatusEnum activityStatus,
			ResponseBase response) throws ArmutServiceException {
		try {
			UserEntity user = userRepository.findByUserName(userRequest.getUserName());
			ActivityLogEntity activityLog = new ActivityLogEntity();
			activityLog.setActivityName(activityName.toString());
			activityLog.setActivityDetail(response.getMessage());
			if (user != null)
			activityLog.setUserId(user.getId());
			activityLog.setActivityStatus(activityStatus.getValue());
			log.error("ARMUT ACTIVITY LOG [INFO]  ********** " + response.getMessage());
			activityLogRepository.save(activityLog);
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

}
