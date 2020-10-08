package com.armut.exception;

public class ArmutControllerException extends BaseException {

	private static final long serialVersionUID = 1L;

	public ArmutControllerException(String message) {
		super(message);
	}
	
	public ArmutControllerException(Exception ex) {
		super(ex.toString());
	}
}
