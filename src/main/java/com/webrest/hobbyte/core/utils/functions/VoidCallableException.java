package com.webrest.hobbyte.core.utils.functions;

@FunctionalInterface
public interface VoidCallableException {
	void call(Exception e);
}
