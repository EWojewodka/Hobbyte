package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.i18n.MessageSourceHelper;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.StringUtils;

@Service
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class RegistrationAjaxForm extends AjaxDynamicForm {

	private ExtranetUserContext context;

	@Autowired
	private ExtranetUserDao userDao;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private MessageSourceHelper msgHelper;
	
	public RegistrationAjaxForm(ExtranetUserContext context) {
		this.context = context;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	protected void process(HttpServletRequest request) throws Exception {

		String login = request.getParameter("login");
		AjaxAsserts.longerThan(login, 5,
				msgHelper.getMessage("Min", context, msgHelper.getMessage("username", context), 6));

		String email = request.getParameter("email");
		String emailMsg = msgHelper.getMessage("NotEmail", context);
		AjaxAsserts.assertTrue(StringUtils.isEmail(email), emailMsg);

		String password = request.getParameter("password");
		AjaxAsserts.longerThan(password, 7,
				msgHelper.getMessage("Min", context, msgHelper.getMessage("password", context), 8));

		AjaxAsserts.assertNull(userDao.findByLogin(login), msgHelper.getMessage("NotAvailableLogin", context));
		AjaxAsserts.assertNull(userDao.findByEmail(email), msgHelper.getMessage("NotAvailableEmail", context));

		ExtranetUser user = new ExtranetUser();
		user.setLogin(login);
		user.setEmail(email);
		user.setPassword(encoder.encode(password));
		userDao.save(user);
		context.loginUser(user);
		setRedirect("/");
	}

	@Override
	public String getCode() {
		return "registration";
	}

}