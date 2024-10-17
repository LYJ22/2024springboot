package com.sample.spring.util;

public class CustomJWTException extends RuntimeException{
	public CustomJWTException(String msg) {
        super(msg);
    }
}
