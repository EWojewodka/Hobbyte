/**
 * 
 */
package com.webrest.hobbyte.core.dynamicForm;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

/**
 * Abstract class for expanding if you want to use simple, ajax form on front
 * view. For example, if you must send only one input to backend, just use this
 * for handle request.
 * 
 * @author Emil Wojewódka
 *
 * @since 15 mar 2018
 */
@Service
public abstract class AjaxDynamicForm {

	@Autowired(required = true)
	private IExtranetUserContext context;

	private JSONObject jsonObject = new JSONObject();

	public String run() {
		String s = ExceptionStream.handle(e -> {
			return handleException(e);
		}).call(() -> {
			process(context);
			return jsonObject.toString();
		}).get();
		System.out.println(s);
		return s;
	}

	// Create manual json object with one property - error.
	private static String createJsonError(String message) {
		return String.format("{\"error\" : \"%s\"}", message);
	}

	private String handleException(Exception e) {
		ExceptionStream.printOnFailure().call(() -> {
			if (e instanceof AjaxMessageException) {
				context.getResponse().sendError(((AjaxMessageException) e).getErrorCode(), e.getMessage());
			} else {
				context.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		});
		return createJsonError(e.getMessage());
	}

	/**
	 * Method with form logic.
	 * 
	 * @return
	 */
	protected abstract void process(IExtranetUserContext context) throws Exception;

	public String getParameter(String name) {
		return context.getRequest().getParameter(name);
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
