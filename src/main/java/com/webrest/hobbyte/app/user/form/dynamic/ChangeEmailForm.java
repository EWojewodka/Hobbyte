/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 15 mar 2018
 */
@Service
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ChangeEmailForm extends UserAjaxForm {


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
