/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.core.dynamicForm.SimpleMessage;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 15 mar 2018
 */
@Service
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ChangeEmailForm extends UserEntityAjaxForm<SimpleMessage> {

	@Override
	protected SimpleMessage process(IExtranetUserContext context) throws Exception {
		valid();

		String newEmail = getParameter("email");
		AjaxAsserts.assertTrue(StringUtils.isEmail(newEmail), "Invalid email format.");
		AjaxAsserts.assertFalse(getUser().getEmail().equals(newEmail), "Email must be different than currently.");

		getUser().setEmail(newEmail);
		getUserDao().save(getUser());
		return new SimpleMessage("Your email has been changed.");
	}

	@Override
	public String getCode() {
		return "email";
	}

}
