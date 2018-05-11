package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class ChangeLastnameForm extends UserAjaxForm {

	public ChangeLastnameForm(DependencyResolver dependencyResolver) {
		super(dependencyResolver);
	}

	@Override
	protected JSONObject process(HttpServletRequest request) throws Exception {
		valid(request);
		String lastname = getParameter("lastname");
		AjaxAsserts.notEmpty(lastname, "We don't think so you're lastnameless!");
		
		ExtranetUser user = getUser();
		user.setLastname(lastname);
		getUserDao().save(user);
		JSONObject result = new JSONObject();
		addMessage(result, "Your lastname is changed!");
		return result;
	}

	@Override
	public String getCode() {
		return "lastname";
	}

}
