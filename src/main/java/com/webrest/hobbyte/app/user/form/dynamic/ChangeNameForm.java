package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class ChangeNameForm extends UserAjaxForm {

	public ChangeNameForm(DependencyResolver dependencyResolver) {
		super(dependencyResolver);
	}

	@Override
	protected void process(HttpServletRequest request) throws Exception {
		valid(request);
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
