package com.armut.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


	private ResponseEntity<ErrorMessage> error(final Exception exception, final HttpStatus httpStatus,
			HttpServletRequest request) {
		return new ResponseEntity<>(new ErrorMessage(exception, request.getRequestURI()), httpStatus);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorMessage> handleRuntimeException(HttpServletRequest request, final RuntimeException e) {
		log.error("ARMUT RuntimeException [ERROR]  ********** " +  ExceptionUtils.getStackTrace(e));
		return error(e, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorMessage> handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
			HttpRequestMethodNotSupportedException e) {
		log.error(
				"ARMUT HttpRequestMethodNotSupportedException [ERROR]  ********** " +  ExceptionUtils.getStackTrace(e));
		return error(e, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(JDBCConnectionException.class)
	public ResponseEntity<ErrorMessage> handleJDBCConnectionException(HttpServletRequest request,
			JDBCConnectionException e) {
		log.error(
				"ARMUT JDBCConnectionException [ERROR]  ********** " +  ExceptionUtils.getStackTrace(e));
		return error(new Exception("DB is downn"), HttpStatus.BAD_REQUEST, request);
	}
	

	@ExceptionHandler(ArmutServiceException.class)
	public ResponseEntity<ErrorMessage> handleArmutServiceException(HttpServletRequest request,
			ArmutServiceException e) {
		log.error("ARMUT SERVICE EXCEPTION [ERROR]  ********** " + e +  ExceptionUtils.getStackTrace(e));
		return error(new Exception("Service Exception, get in touch with the provider"), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(ArmutControllerException.class)
	public ResponseEntity<ErrorMessage> handleArmutControllerException(HttpServletRequest request,
			ArmutControllerException e) {
	
		log.error("ARMUT CONTROLLER EXCEPTION [ERROR]  ********** " + ExceptionUtils.getStackTrace(e));
		return error(new Exception("Controller Exception, get in touch with the provider"), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}