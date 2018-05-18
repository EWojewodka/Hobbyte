package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.utils.AjaxAsserts;

@Service
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ChangeLastnameForm extends UserAjaxForm {

	@Override
	protected void process(HttpServletRequest request) throws Exception {
		valid(request);
		String lastname = getParameter("lastname");
		AjaxAsserts.notEmpty(lastname, "We don't think so you're lastnameless!");
		
		ExtranetUser user = getUser();
		user.setLastname(lastname);
		getUserDao().save(user);
		addMessage("Your lastname is changed!");
	}

	@Override
	public String getCode() {
		return "lastname";
	}

}
