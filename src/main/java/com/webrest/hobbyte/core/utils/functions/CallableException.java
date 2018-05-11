package com.webrest.hobbyte.core.utils.functions;

public interface CallableException<T> {

	T call(Exception e);
	
}

interface VoidCallableException {
	void call(Exception e);
}
