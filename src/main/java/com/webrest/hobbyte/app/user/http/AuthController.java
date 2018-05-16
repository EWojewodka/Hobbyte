package com.webrest.hobbyte.app.user.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserStatus;
import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.dynamicForm.AjaxFormFactory;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.i18n.MessageSourceHelper;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.HttpUtils;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

@Controller
@RequestMapping(value = "/auth")
public class AuthController extends BaseController {

	@Autowired
	private AjaxFormFactory formFactory;
	
	@GetMapping(value = "/sign-in")
	public String getSignIn(Model model) {
		return "redirect:/";
	}

	@PostMapping(value = "/sign-in")
	@ResponseBody
	public String postSignIn() throws Exception {
		RegistrationAjaxFrom registrationAjaxFrom = new RegistrationAjaxFrom(getContext(), getDependencyResolver());
		String result = registrationAjaxFrom.run(getContext());
		return result;
	}

	@GetMapping(value = "/sign-up")
	public String getSignUp(Model model) {
		return "redirect:/";
	}

	@PostMapping(value = "/sign-up")
	@ResponseBody
	public String postSignUp() {
		AjaxDynamicForm form = formFactory.getForm("sign-up");
		form.run(getContext());
		ExtranetUser user = loginAjaxForm.getUser();
		if (user != null)
			getContext().loginUser(user);
		return result;
	}

}



class RegistrationAjaxFrom extends AjaxDynamicForm {

	private ExtranetUserContext context;

	public RegistrationAjaxFrom(ExtranetUserContext context, DependencyResolver dependencyResolver) {
		super(dependencyResolver);
		this.context = context;
	}
	
	@Override
	public Class<?>[] getDependencies() {
		return ArrayUtils.addAll(super.getDependencies(), new Class<?>[] {ExtranetUserDao.class, PasswordEncoder.class, MessageSourceHelper.class});
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	protected void process(HttpServletRequest request) throws Exception {
		ExtranetUserDao userDao = getDependency(ExtranetUserDao.class);
		MessageSourceHelper messageHelper = getDependency(MessageSourceHelper.class);

		String login = request.getParameter("login");
		AjaxAsserts.longerThan(login, 5,
				messageHelper.getMessage("Min", context, messageHelper.getMessage("username", context), 6));

		String email = request.getParameter("email");
		String emailMsg = messageHelper.getMessage("NotEmail", context);
		AjaxAsserts.assertTrue(StringUtils.isEmail(email), emailMsg);

		String password = request.getParameter("password");
		AjaxAsserts.longerThan(password, 7, messageHelper.getMessage("Min", context, messageHelper.getMessage("password", context), 8));

		AjaxAsserts.assertNull(userDao.findByLogin(login), messageHelper.getMessage("NotAvailableLogin", context));
		AjaxAsserts.assertNull(userDao.findByEmail(email), messageHelper.getMessage("NotAvailableEmail", context));
		
		ExtranetUser user = new ExtranetUser();
		user.setLogin(login);
		user.setEmail(email);
		user.setPassword(getDependency(PasswordEncoder.class).encode(password));
		userDao.save(user);
		context.loginUser(user);
		setRedirect("/");
	}


	@Override
	public String getCode() {
		return "registration";
	}

}
