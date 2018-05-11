package com.webrest.hobbyte.core.utils.functions;

@FunctionalInterface
public interface TryStream<T> {

	T tryAdvice(Callable<T> callable);
	
}
