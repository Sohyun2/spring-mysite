package com.douzone.mysite.exception;

public class UserDaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public UserDaoException() {
		super("UserDao 예외");
	}
	
	public UserDaoException(String message) {
		super(message);
	}
	
}
