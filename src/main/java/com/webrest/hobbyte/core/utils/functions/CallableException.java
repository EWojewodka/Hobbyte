package com.webrest.hobbyte.core.utils.functions;

@FunctionalInterface
public interface CallableException<T> {

	T call(Exception e);
	
}