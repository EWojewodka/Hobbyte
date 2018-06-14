package com.webrest.hobbyte.app.user.form.dynamic;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.app.socialMedia.SocialSourceRegistration;
import com.webrest.hobbyte.app.socialMedia.facebook.FacebookRegistrationSource;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.GenericAjaxDynamicForm;
import com.webrest.hobbyte.core.dynamicForm.SimpleMessage;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;

@Service
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RegistrationAjaxForm extends GenericAjaxDynamicForm<SimpleMessage> {

	@Autowired
	private ExtranetUserDao userDao;

	@Resource(name = "defaultRegistration")
	private DefaultRegistrationSource defaultRegistration;

	@Resource(name = "facebookRegistration")
	private FacebookRegistrationSource facebookRegistration;

	@Override
	@Transactional(rollbackOn = Exception.class)
	protected SimpleMessage process(IExtranetUserContext context) throws Exception {
		String source = getParameter("source");
		SocialSourceRegistration registrationStrategy = null;

		if ("facebook".equals(source))
			registrationStrategy = facebookRegistration;
		else
			registrationStrategy = defaultRegistration;

		registrationStrategy.validate();
		ExtranetUser user = registrationStrategy.buildUser();
		LoginAjaxForm.handleRememberMe(getContext(), user, userDao);
		userDao.save(user);
		context.loginUser(user);
		return new SimpleMessage().setRedirect("/");
	}

	@Override
	public String getCode() {
		return "registration";
	}

	@Override
	protected SimpleMessage handleException(Exception e) {
		return new SimpleMessage().addError(e.getMessage());
	}

}