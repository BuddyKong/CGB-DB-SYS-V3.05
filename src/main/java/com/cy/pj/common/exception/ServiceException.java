package com.cy.pj.common.exception;
/**
 * 自定义业务异常
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -9085326160255400760L;
	public ServiceException() {
		super();
	}
	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	public ServiceException(String message) {
		super(message);
	}
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
