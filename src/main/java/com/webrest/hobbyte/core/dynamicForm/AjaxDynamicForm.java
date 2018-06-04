/**
 * 
 */
package com.webrest.hobbyte.core.dynamicForm;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.webrest.hobbyte.app.user.form.dynamic.UserEntityAjaxForm;
import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

/**
 * Abstract class for expanding if you want to use simple, ajax form on front
 * view. For example, if you must send only one input to backend, just use this
 * for handle request.
 * 
 * <b>Deprecated: </b> better use {@link GenericAjaxDynamicForm} or
 * {@link UserEntityAjaxForm}. Custom java<=>json converter wasn't good idea. :(
 * 
 * @author Emil Wojewódka
 *
 * @since 15 mar 2018
 */
@Deprecated
public abstract class AjaxDynamicForm extends GenericAjaxDynamicForm<String> {

	private JSONObject jsonObject = new JSONObject();

	// Create manual json object with one property - error.
	private static String createJsonError(String message) {
		return String.format("{\"error\" : \"%s\"}", message);
	}

	@Override
	protected String handleException(Exception e) {
		ExceptionStream.printOnFailure().call(() -> {
			if (e instanceof AjaxMessageException) {
				getContext().getResponse().sendError(((AjaxMessageException) e).getErrorCode(), e.getMessage());
			} else {
				getContext().getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		});
		return createJsonError(e.getMessage());
	}

	protected void addMessage(String message) throws JSONException {
		addJsonValue("msg", message);
	}

	protected void setRedirect(String redirectPath) throws JSONException {
		addJsonValue("redirect", redirectPath);
	}

	protected void addJsonValue(String key, Object value) throws JSONException {
		jsonObject.put(key, value);
	}

	protected JSONObject getJSON() {
		return jsonObject;
	}

}
