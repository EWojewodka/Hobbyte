/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import com.webrest.hobbyte.core.utils.AjaxAsserts;
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
	protected void process(HttpServletRequest request) throws Exception {
		valid(request);
		
		String newEmail = getParameter("email");
		AjaxAsserts.assertTrue(StringUtils.isEmail(newEmail), "Invalid email format.");
		AjaxAsserts.assertFalse(getUser().getEmail().equals(newEmail), "Email must be different than currently.");

		getUser().setEmail(newEmail);
		getUserDao().save(getUser());
		addMessage("Your email has been changed.");
	}

	@Override
	public String getCode() {
		return "email";
	}

}
