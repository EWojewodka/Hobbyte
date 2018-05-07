package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class ChangeNameForm extends UserAjaxForm {

	public ChangeNameForm(DependencyResolver dependencyResolver) {
		super(dependencyResolver);
	}

	@Override
	protected JSONObject process(HttpServletRequest request) throws Exception {
		valid(request);
		String name = getParameter("name");
		if (StringUtils.isEmpty(name))
			throw new AjaxMessageException("We don't think so you has no name!", HttpServletResponse.SC_BAD_REQUEST);
		ExtranetUser user = getUser();
		user.setName(name);
		getUserDao().save(user);
		JSONObject result = new JSONObject();
		addMessage(result, "Hello " + name + "! Your name is changed!");
		return result;
	}

	@Override
	public String getCode() {
		return "name";
	}

}
