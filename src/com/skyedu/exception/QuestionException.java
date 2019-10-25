package com.skyedu.exception;

public class QuestionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7997375777232555507L;

	private int code;

	public QuestionException(String message) {
		super(message);
	}
	
	public QuestionException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
