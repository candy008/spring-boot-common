package com.imfbp.boot.common.utils.exception;

public class ResultException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResultException(){
		super();
	}
	
	/**
	 * @param message
	 */
	public ResultException(String message){
		super(message);
	}
	
	/**
	 * @param cause
	 */
	public ResultException(Throwable cause){
		super(cause);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public ResultException(String message,Throwable cause){
		super(message, cause);
	}
	
	public ResultException(String errorCode,String message,Throwable cause){
		super("错误编码:"+errorCode+",错误信息："+message+","+cause.getMessage(),cause);
	}
	
}
