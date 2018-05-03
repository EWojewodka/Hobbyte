package com.webrest.hobbyte.app.user.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.json.JSONObject;
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
import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.utils.HttpUtils;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

@Controller
@RequestMapping(value = "/auth")
public class AuthController extends BaseController {

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
		LoginAjaxForm loginAjaxForm = new LoginAjaxForm(getContext(), getDependencyResolver());
		String result = loginAjaxForm.run(getContext());
		ExtranetUser user = loginAjaxForm.getUser();
		if (user != null)
			getContext().loginUser(user);
		return result;
	}

}

class LoginAjaxForm extends AjaxDynamicForm {

	private ExtranetUserContext context;

	private ExtranetUser user;

	public LoginAjaxForm(ExtranetUserContext context, DependencyResolver dependencyResolver) {
		super(dependencyResolver);
		this.context = context;
	}

	@Override
	protected JSONObject process(HttpServletRequest request) throws Exception {
		JSONObject resultJson = new JSONObject();
		boolean isLogged = ExtranetUserUtils.isLogged(request);
		if (isLogged) {
			setRedirect(resultJson, "/");
			return resultJson;
		}
		ExtranetUser u = findUser(request);
		if (u == null)
			throw new AjaxMessageException("The login or password you entered is incorrect", HttpServletResponse.SC_BAD_REQUEST);

		if (!getDependency(PasswordEncoder.class).matches(request.getParameter("password"), u.getPassword()))
			throw new AjaxMessageException("The login or password you entered is incorrect", HttpServletResponse.SC_BAD_REQUEST);

		if (u.getStatus() != ExtranetUserStatus.ACTIVE)
			throw new AjaxMessageException("UserNotActive", HttpServletResponse.SC_BAD_REQUEST);

		this.user = u;
		handleRememberMe(request, user);
		setRedirect(resultJson, "/");
		return resultJson;
	}

	@Override
	public Class<?>[] getDependencies() {
		return new Class<?>[] { ExtranetUserDao.class, PasswordEncoder.class };
	}

	private ExtranetUser findUser(HttpServletRequest request) {
		return getDependency(ExtranetUserDao.class).findByLoginOrEmail(request.getParameter("login"));
	}

	public void handleRememberMe(HttpServletRequest request, ExtranetUser user) {
		HttpUtils.removeCookieIfExists(ExtranetUserUtils.REMEMBER_ME_COOKIE_NAME, context);

		String code = StringUtils.generateRandom(250);
		user.setRememberMeCode(code);
		getDependency(ExtranetUserDao.class).save(user);

		Cookie cookie = new Cookie(ExtranetUserUtils.REMEMBER_ME_COOKIE_NAME, code);
		HttpUtils.setMaxAgeCookie(cookie);
		cookie.setPath("/");
		context.getResponse().addCookie(cookie);
	}

	public ExtranetUser getUser() {
		return user;
	}

	@Override
	public String getCode() {
		return "header-sign-in";
	}

}

class RegistrationAjaxFrom extends AjaxDynamicForm {

	private ExtranetUserContext context;
	
	public RegistrationAjaxFrom(ExtranetUserContext context, DependencyResolver dependencyResolver) {
		super(dependencyResolver);
		this.context = context;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	protected JSONObject process(HttpServletRequest request) throws Exception {
		ExtranetUserDao userDao = getDependency(ExtranetUserDao.class);
		
		String login = request.getParameter("login");
		if (StringUtils.isEmpty(login) || login.length() < 6)
			throw new AjaxMessageException("Login cannot be shorter than 6 characters",
					HttpServletResponse.SC_BAD_REQUEST);
	
		String email = request.getParameter("email");
		if (StringUtils.isEmpty(email) || !StringUtils.isEmail(email))
			throw new AjaxMessageException("Wrong email format", HttpServletResponse.SC_BAD_REQUEST);
	
		String password = request.getParameter("password");
		if (StringUtils.isEmpty(password) || password.length() < 8)
			throw new AjaxMessageException("Password cannot be shorter than 8 characters",
					HttpServletResponse.SC_BAD_REQUEST);
		
		if(userDao.findByLogin(login) != null)
			throw new AjaxMessageException("This login is not available", HttpServletResponse.SC_BAD_REQUEST);
		
		if(userDao.findByEmail(email) != null)
			throw new AjaxMessageException("This email is not available", HttpServletResponse.SC_BAD_REQUEST);

		ExtranetUser user = new ExtranetUser();
		user.setLogin(login);
		user.setEmail(email);
		user.setPassword(getDependency(PasswordEncoder.class).encode(password));
		userDao.save(user);
		context.loginUser(user);
		JSONObject result = new JSONObject();
		setRedirect(result, "/");
		return result;
	}

	@Override
	public Class<?>[] getDependencies() {
		return new Class<?>[] { ExtranetUserDao.class, PasswordEncoder.class };
	}

	@Override
	public String getCode() {
		return "registration";
	}

}
