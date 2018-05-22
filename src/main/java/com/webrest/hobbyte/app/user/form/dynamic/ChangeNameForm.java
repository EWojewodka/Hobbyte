package com.webrest.hobbyte.app.user.form.dynamic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.utils.AjaxAsserts;

@Service
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ChangeNameForm extends UserAjaxForm {

	@Override
	protected void process(IExtranetUserContext context) throws Exception {
		valid(context.getRequest());

		String name = getParameter("name");
		AjaxAsserts.notEmpty(name, "We don't think so you has no name!");

		ExtranetUser user = getUser();
		user.setName(name);
		getUserDao().save(user);
		addMessage(String.format("Hello %s! Your name is changed!", name));
	}

	@Override
	public String getCode() {
		return "name";
	}

}
