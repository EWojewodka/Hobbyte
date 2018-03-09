package com.webrest.hobbyte.core.exception;

public class NoStackTraceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8373822295822171961L;

	public NoStackTraceException() {
		
	}
	
	public NoStackTraceException(String message) {
		super(message);
	}
	
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

	@Override
	public String toString() {
		return "";
	}
    
    
}
