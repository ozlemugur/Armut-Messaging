package com.armut.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//	@Autowired
//	private ExceptionLogService exceptionLogService;

	private ResponseEntity<ErrorMessage> error(final Exception exception, final HttpStatus httpStatus,
			HttpServletRequest request) {
//		ExceptionLog exceptionLog = new ExceptionLog();
//		exceptionLog.setException(exception.toString());
//		exceptionLogService.logException(exceptionLog);
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

//	@ExceptionHandler(ExceptionLogDbInsertionException.class)
//	public ResponseEntity<ErrorMessage> handleExceptionLogDbInsertionException(HttpServletRequest request,
//			ExceptionLogDbInsertionException e) throws ExceptionLogDbInsertionException {
//		System.out.print(e.toString());
//		return error(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
//	}

	@ExceptionHandler(ArmutServiceException.class)
	public ResponseEntity<ErrorMessage> handleArmutServiceException(HttpServletRequest request,
			ArmutServiceException e) {
		log.error("ARMUT SERVICE EXCEPTION [ERROR]  ********** " + e +  ExceptionUtils.getStackTrace(e));
		return error(null, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(ArmutControllerException.class)
	public ResponseEntity<ErrorMessage> handleArmutControllerException(HttpServletRequest request,
			ArmutControllerException e) {
	
		log.error("ARMUT CONTROLLER EXCEPTION [ERROR]  ********** " + ExceptionUtils.getStackTrace(e));
		return error(new Exception(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}