package com.armut.exception;

public class ArmutServiceException extends BaseException {

	private static final long serialVersionUID = 1L;

	public ArmutServiceException(String message, long userId, Exception ex) {
		super(message);
	}

	public ArmutServiceException(String message) {
		super(message);
	}

	public ArmutServiceException(Long userId, Exception ex) {
		super(ex.toString());
	}
	
	public ArmutServiceException(String userName, Exception ex) {
		super(ex.toString());
	}
	
	public ArmutServiceException(Exception ex) {
		super(ex.toString());
	}

}
