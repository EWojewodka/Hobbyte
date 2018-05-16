package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserStatus;
import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.i18n.MessageSourceHelper;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.HttpUtils;
import com.webrest.hobbyte.core.utils.StringUtils;

@Service
public class LoginAjaxForm extends AjaxDynamicForm {

	private ExtranetUserContext context;

	private ExtranetUser user;

	@Autowired
	private ExtranetUserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSourceHelper msgHelper;

	public LoginAjaxForm(ExtranetUserContext context) {
		this.context = context;
	}

	@Override
	protected void process(HttpServletRequest request) throws Exception {
		if (ExtranetUserUtils.isLogged(request)) {
			setRedirect("/");
			return;
		}
		ExtranetUser u = userDao.findByLoginOrEmail(request.getParameter("login"));

		AjaxAsserts.notNull(u, msgHelper.getMessage("IncorrectLogin", context));

		boolean isPasswordMatches = passwordEncoder.matches(request.getParameter("password"), u.getPassword());
		AjaxAsserts.assertTrue(isPasswordMatches, msgHelper.getMessage("IncorrectLogin", context));

		AjaxAsserts.assertTrue(u.getStatus() == ExtranetUserStatus.ACTIVE,
				msgHelper.getMessage("UserNotActive", context));

		this.user = u;
		handleRememberMe(request, user);
		setRedirect("/");
	}

	public void handleRememberMe(HttpServletRequest request, ExtranetUser user) {
		HttpUtils.removeCookieIfExists(ExtranetUserUtils.REMEMBER_ME_COOKIE_NAME, context);

		String code = StringUtils.generateRandom(250);
		user.setRememberMeCode(code);
		userDao.save(user);

		Cookie cookie = new Cookie(ExtranetUserUtils.REMEMBER_ME_COOKIE_NAME, code);
		HttpUtils.setMaxAgeCookie(cookie);
		cookie.setPath("/");
		context.getResponse().addCookie(cookie);
	}

	@Override
	public String getCode() {
		return "sign-up";
	}

}