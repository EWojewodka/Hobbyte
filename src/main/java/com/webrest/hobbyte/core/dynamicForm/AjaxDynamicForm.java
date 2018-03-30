/**
 * 
 */
package com.webrest.hobbyte.core.dynamicForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.webrest.hobbyte.core.exception.AjaxMessageException;

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

	private HttpServletRequest request;

	private HttpServletResponse response;

	public String run(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		try {
			return process(request).toString();
		} catch (Exception e) {
			try {
				return handleException(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return "";
	}

	// Create manual json object with one property - error.
	private static String createJsonError(String message) {
		return String.format("{\"error\" : \"%s\"}", message);
	}

	private String handleException(Exception e) throws Exception {
		if (e instanceof AjaxMessageException) {
			response.sendError(((AjaxMessageException) e).getErrorCode(), e.getMessage());
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return createJsonError(e.getMessage());
	}

	/**
	 * Method with form logic.
	 * 
	 * @return
	 */
	protected abstract JSONObject process(HttpServletRequest request) throws Exception;

	public String getParameter(String name) {
		return request.getParameter(name);
	}

	public abstract String getCode();
	
	protected void addMessage(JSONObject jsonObject, String message) throws JSONException {
		jsonObject.put("msg", message);
	}

}
