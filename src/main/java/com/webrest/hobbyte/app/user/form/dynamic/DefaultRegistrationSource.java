/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.app.socialMedia.SocialSourceRegistration;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.i18n.MessageSourceHelper;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 12 cze 2018
 */
@Service("defaultRegistration")
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DefaultRegistrationSource implements SocialSourceRegistration {

	@Autowired(required = true)
	private ExtranetUserContext context;

	@Autowired
	protected MessageSourceHelper msgHelper;

	@Autowired
	protected ExtranetUserDao userDao;

	@Autowired
	protected PasswordEncoder encoder;

	@Override
	public void validate() throws Exception {
		String login = getParameter("login");
		AjaxAsserts.longerThan(login, 5,
				msgHelper.getMessage("Min", context, msgHelper.getMessage("username", context), 6));

		String email = getParameter("email");
		String emailMsg = msgHelper.getMessage("NotEmail", context);
		AjaxAsserts.assertTrue(StringUtils.isEmail(email), emailMsg);

		String password = getParameter("password");
		AjaxAsserts.longerThan(password, 7,
				msgHelper.getMessage("Min", context, msgHelper.getMessage("password", context), 8));

		AjaxAsserts.assertNull(userDao.findByLogin(login), msgHelper.getMessage("NotAvailableLogin", context));
		AjaxAsserts.assertNull(userDao.findByEmail(email), msgHelper.getMessage("NotAvailableEmail", context));
	}

	@Override
	public ExtranetUser buildUser() {
		ExtranetUser user = new ExtranetUser();
		user.setEmail(getParameter("email"));
		user.setLogin(getParameter("login"));
		user.setPassword(encoder.encode(getParameter("password")));
		return user;
	}

	protected ExtranetUserContext getContext() {
		return context;
	}

	protected String getParameter(String parameter) {
		return context.getRequest().getParameter(parameter);
	}

}
