package com.webrest.hobbyte.app.user.form.dynamic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.SimpleMessage;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.utils.AjaxAsserts;

@Service
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ChangeLastnameForm extends UserEntityAjaxForm<SimpleMessage> {

	@Override
	protected SimpleMessage process(IExtranetUserContext context) throws Exception {
		valid();
		
		String lastname = getParameter("lastname");
		AjaxAsserts.notEmpty(lastname, "We don't think so you're lastnameless!");
		
		ExtranetUser user = getUser();
		user.setLastname(lastname);
		getUserDao().save(user);
		return new SimpleMessage().addMessage("Your lastname is changed!");
	}

	@Override
	public String getCode() {
		return "lastname";
	}

}
