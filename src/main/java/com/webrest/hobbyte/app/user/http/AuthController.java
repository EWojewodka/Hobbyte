package com.webrest.hobbyte.app.user.http;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.webrest.hobbyte.app.user.form.LoginForm;
import com.webrest.hobbyte.app.user.form.RegistrationForm;
import com.webrest.hobbyte.app.user.service.UserLoginService;
import com.webrest.hobbyte.app.user.service.UserRegistrationService;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.utils.MessageUtils;

@Controller
public class AuthController extends BaseController {

	@Autowired
	private UserRegistrationService registrationService;

	@Autowired
	private UserLoginService loginService;

	/**
	 * Render registration form
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/auth/sign-in")
	public String getSignIn(Model model) {
		model.addAttribute("registrationForm", new RegistrationForm());
		return "auth/sign_in";
	}

	/**
	 * Validate registration form and create user.
	 * 
	 * @param form
	 * @param binding
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/auth/sign-in")
	public String postSignIn(@Valid @ModelAttribute RegistrationForm form, final BindingResult binding, Model model) throws Exception {
		registrationService.addForm(form);
		if (binding.hasErrors() || !registrationService.isValid(binding)) {
			return "auth/sign_in";
		}
		
		// Auto login after registration
		((IExtranetUserContext) getContext()).loginUser(registrationService.createFromForm());
		//Render success registration message
		model.addAttribute("message", MessageUtils.getSimpleMessage("/registration.txt"));
		return "info/success";
	}

	/**
	 * Render login form
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/auth/sign-up")
	public String getSignUp(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "auth/sign_up";
	}

	/**
	 * Check login form and login user.
	 * 
	 * @param form
	 * @param binding
	 * @return
	 */
	@PostMapping(value = "/auth/sign-up")
	public String postSignUp(@Valid @ModelAttribute LoginForm form, final BindingResult binding) {
		loginService.addForm(form);
		if (binding.hasErrors() || !loginService.isValid(binding)) {
			return "auth/sign_up";
		}
		loginService.handleRememberMe();
		((IExtranetUserContext) getContext()).loginUser(loginService.getUser());
		return "redirect:/";
	}

}
