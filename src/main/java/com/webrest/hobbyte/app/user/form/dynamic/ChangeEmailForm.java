/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * @author Emil Wojewódka
 *
 * @since 15 mar 2018
 */
public class ChangeEmailForm extends UserAjaxForm {

	public ChangeEmailForm(DependencyResolver dependencyResolver) {
		super(dependencyResolver);
	}

	@Override
	protected JSONObject process(HttpServletRequest request) throws Exception {
		valid(request);
		
		String newEmail = getParameter("email");
		if (!StringUtils.isEmail(newEmail))
			throw new AjaxMessageException("Invalid email format.", HttpServletResponse.SC_BAD_REQUEST);

		if (getUser().getEmail().equals(newEmail))
			throw new AjaxMessageException("Email must be different than currently.",
					HttpServletResponse.SC_BAD_REQUEST);

		getUser().setEmail(newEmail);
		getUserDao().save(getUser());
		JSONObject result = new JSONObject();
		addMessage(result, "Your email has been changed.");
		return result;
	}

	@Override
	public String getCode() {
		return "email";
	}

}
