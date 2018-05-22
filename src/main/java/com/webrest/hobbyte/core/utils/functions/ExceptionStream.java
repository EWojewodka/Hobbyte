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
@SuppressWarnings("unchecked")
public class ExceptionStream {

	private Object result;

	private CallableException<?> actionOnException;

	private VoidCallableException actionOnVoidException;

	private boolean isThrow = false;

	private ExceptionStream() {

	}

	public <T> T get() {
		return (T) result;
	}

	public <T> T getOrDefault(Object object) {
		return result == null ? (T) object : (T) result;
	}

	public static ExceptionStream handle(CallableException<?> callable) {
		ExceptionStream es = new ExceptionStream();
		es.actionOnException = callable;
		return es;
	}

	public static ExceptionStream handle(VoidCallableException callable) {
		ExceptionStream es = new ExceptionStream();
		es.actionOnVoidException = callable;
		return es;
	}

	/**
	 * Print {@link Exception} by {@link Exception#printStackTrace()} if
	 * {@link #call(Callable)} or {@link #call(VoidCallable)} throws it.
	 * 
	 * @return
	 */
	public static ExceptionStream printOnFailure() {
		ExceptionStream es = new ExceptionStream();

		return es;
	}

	public ExceptionStream call(Callable<?> callable) {
		try {
			result = callable.call();
		} catch (Exception e) {
			isThrow = true;
			e.printStackTrace();
			result = actionOnException != null ? actionOnException.call(e) : null;
		}
		return this;
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
			isThrow = true;
			e.printStackTrace();
			if (actionOnVoidException != null) {
				actionOnVoidException.call(e);
			}
		}
	}

	public boolean isThrow() {
		return isThrow;
	}

}
