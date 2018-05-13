package com.webrest.hobbyte.core.utils.functions;

/**
 * Simple class for catching easy {@link Exception}s. Sometimes we have a
 * something like this
 * 
 * <pre>
 * try {
 * 	// invoke one method which throws unchecked exception.
 * } catch (Exception e) {
 * 	e.printStackTrace();
 * }
 * </pre>
 * 
 * Now we can do something like below example: </br>
 * <code>{@link ExceptionStream}.handle(e -> LOGGER.info(e.getMessage())).call(() ->
 * [your bussiness logic])</code>. </br>
 * or shorter for only print {@link Exception#printStackTrace()} </br>
 * </code>{@link ExceptionStream}.print().call(() -> [your bussiness logic]);
 * </code> </br>
 * 
 * <i>Note: You could do something like this: </br>
 * <code>
 * ExceptionStream.handle(e -> { return e.getMessage().equals("a") ? new Object : null}).call(() -> {return new ExpectedObject();});
 * </code> </i>
 * 
 * @author wojew
 *
 * @param <T>
 */
public class ExceptionStream<T> {

	private T result;

	private CallableException<?> actionOnException;

	private VoidCallableException actionOnVoidException;

	private ExceptionStream() {

	}

	public T get() {
		return result;
	}

	public T getOrDefault(T object) {
		return result == null ? object : result;
	}

	public static <T> ExceptionStream<T> handle(CallableException<T> callable) {
		ExceptionStream<T> es = new ExceptionStream<>();
		es.actionOnException = callable;
		return es;
	}

	public static ExceptionStream<?> handle(VoidCallableException callable) {
		ExceptionStream<?> es = new ExceptionStream<>();
		es.actionOnVoidException = callable;
		return es;
	}

	/**
	 * Print {@link Exception} by {@link Exception#printStackTrace()} if
	 * {@link #call(Callable)} or {@link #call(VoidCallable)} throws it.
	 * 
	 * @return
	 */
	public static ExceptionStream<?> printOnFailure() {
		return new ExceptionStream<>();
	}

	@SuppressWarnings("unchecked")
	public T call(Callable<?> callable) {
		try {
			return (T) callable.call();
		} catch (Exception e) {
			if (actionOnException != null) {
				return (T) actionOnException.call(e);
			} else {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Call action without return values.
	 * 
	 * @see #call(Callable)
	 * @see VoidCallable
	 * @param callable
	 */
	public void call(VoidCallable callable) {
		try {
			callable.call();
		} catch (Exception e) {
			if (actionOnVoidException != null) {
				actionOnVoidException.call(e);
			} else {
				e.printStackTrace();
			}
		}
	}

}
