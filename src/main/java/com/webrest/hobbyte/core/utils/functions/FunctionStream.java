package com.webrest.hobbyte.core.utils.functions;

public class FunctionStream<T> {

	private T result;

	private CallableException actionOnException;

	private FunctionStream() {

	}

	public T get() {
		return result;
	}

	public T getOrDefault(T object) {
		return result == null ? object : result;
	}

	public FunctionStream<T> onException(CallableException<T> callable) {
		this.actionOnException = callable;
		return this;
	}

	public FunctionStream<T> onException(VoidCallableException callable) {
		this.actionOnException = callable;
		return this;
	}
	
	public static <T> FunctionStream<T> prepare() {
		return new FunctionStream<T>();
	}
	
	public static <T> FunctionStream<T> prepare(CallableException<T> callable) {
		FunctionStream<T> stream = new FunctionStream<>();
		stream.onException(callable);
		return stream;
	}
	
	public static <T> FunctionStream<T> prepare(VoidCallableException callable) {
		FunctionStream<T> stream = new FunctionStream<>();
		stream.onException(callable);
		return stream;
	}

	public FunctionStream<T> call(Callable<T> callable) {
		try {
			result = callable.call();
		} catch (Exception e) {
			if (actionOnException != null)
				result = actionOnException.call(e);
			else
				e.printStackTrace();
		}
		return this;
	}
	
	public FunctionStream<T> call(VoidCallable callable) {
		try {
			callable.call();
		} catch (Exception e) {
			if (actionOnException != null)
				actionOnException.call(e);
			else
				e.printStackTrace();
		}
		return this;
	}

}
