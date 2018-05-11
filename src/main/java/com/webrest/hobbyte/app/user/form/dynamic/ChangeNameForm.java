package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class ChangeNameForm extends UserAjaxForm {

	public ChangeNameForm(DependencyResolver dependencyResolver) {
		super(dependencyResolver);
	}

	@Override
	protected JSONObject process(HttpServletRequest request) throws Exception {
		valid(request);
		String name = getParameter("name");
		AjaxAsserts.notEmpty(name, "We don't think so you has no name!");
		
		ExtranetUser user = getUser();
		user.setName(name);
		getUserDao().save(user);
		JSONObject result = new JSONObject();
		addMessage(result, String.format("Hello %s! Your name is changed!", name));
		return result;
	}

	@Override
	public String getCode() {
		return "name";
	}

}
