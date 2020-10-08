package com.armut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.armut.common.ActivityEnum;
import com.armut.common.ServiceBase;
import com.armut.exception.ArmutServiceException;
import com.armut.model.ActivityLogEntity;
import com.armut.model.UserEntity;
import com.armut.repository.ActivityLogRepository;
import com.armut.repository.UserRepository;
import com.armut.request.UserRequest;

@Service
public class ActivityLogService extends ServiceBase{

	@Autowired
	private ActivityLogRepository activityLogRepository;
	
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
	public void insertActivityLog(UserEntity user, ActivityEnum activityName, int activityStatus ) throws ArmutServiceException {
		try {
			ActivityLogEntity activityLog = new ActivityLogEntity();
			activityLog.setActivityName(activityName.toString());
			activityLog.setActivityStatus(activityStatus);
			activityLog.setUserId(user.getId());
			activityLogRepository.save(activityLog);
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertActivityLog(String userName, ActivityEnum activityName, int activityStatus ) throws ArmutServiceException {
		try {
			UserEntity user = userRepository.findByUserName(userName);
			ActivityLogEntity activityLog = new ActivityLogEntity();
			activityLog.setActivityName(activityName.toString());
			activityLog.setActivityStatus(activityStatus);
			activityLog.setUserId(user.getId());
			activityLogRepository.save(activityLog);
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertActivityLog(UserRequest userRequest, ActivityEnum activityName, int activityStatus ) throws ArmutServiceException {
		try {
			UserEntity user = userRepository.findByUserName(userRequest.getUserName());
			ActivityLogEntity activityLog = new ActivityLogEntity();
			activityLog.setActivityName(activityName.toString());
			activityLog.setActivityStatus(activityStatus);
			activityLog.setUserId(user.getId());
			activityLogRepository.save(activityLog);
		} catch (Exception ex) {
			throw new ArmutServiceException(ex);
		}
	}

}
