/**
 * 
 */
package com.webrest.hobbyte.core.dynamicForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.http.context.IHttpContext;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * Abstract class for expanding if you want to use simple, ajax form on front
 * view. For example, if you must send only one input to backend, just use this
 * for handle request.
 * 
 * @author Emil Wojewódka
 *
 * @since 15 mar 2018
 */
public abstract class AjaxDynamicForm {

	public AjaxDynamicForm() {
		AjaxFormFactory.registerForm(this);
	}

	private HttpServletRequest request;

	private HttpServletResponse response;
	
	private JSONObject jsonObject = new JSONObject();

	public String run(IHttpContext context) {
		return run(context.getRequest(), context.getResponse());
	}

	public String run(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		return ExceptionStream.handle(e -> {return handleException(e);}).call(() -> {
			process(request);
			return jsonObject.toString();
		}).get();
	}

	// Create manual json object with one property - error.
	private static String createJsonError(String message) {
		return String.format("{\"error\" : \"%s\"}", message);
	}

	private String handleException(Exception e) {
		ExceptionStream.printOnFailure().call(() -> {
			if (e instanceof AjaxMessageException) {
				response.sendError(((AjaxMessageException) e).getErrorCode(), e.getMessage());
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		});
		return createJsonError(e.getMessage());
	}

	/**
	 * Method with form logic.
	 * 
	 * @return
	 */
	protected abstract void process(HttpServletRequest request) throws Exception;

	public String getParameter(String name) {
		return request.getParameter(name);
	}

	public abstract String getCode();

	protected void addMessage(String message) throws JSONException {
		addJsonValue("msg", message);
	}

	protected void setRedirect(String redirectPath) throws JSONException {
		addJsonValue("redirect", redirectPath);
	}

	protected void addJsonValue(String key, String value) throws JSONException {
		jsonObject.put(key, value);
	}

	protected JSONObject getJSON() {
		return jsonObject;
	}
	
}
