package com.webrest.hobbyte.app.user.http;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webrest.hobbyte.app.user.form.LoginForm;
import com.webrest.hobbyte.app.user.form.RegistrationForm;
import com.webrest.hobbyte.app.user.service.UserLoginService;
import com.webrest.hobbyte.app.user.service.UserRegistrationService;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.utils.MessageUtils;

@Controller
@RequestMapping(value = "/auth")
public class AuthController extends BaseController {

	@Autowired
	private UserRegistrationService registrationService;

	@Autowired
	private UserLoginService loginService;

	@GetMapping(value = "/sign-in")
	public String getSignIn(Model model) {
		return "redirect:/";
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
	@PostMapping(value = "/sign-in")
	public String postSignIn(@Valid @ModelAttribute("registrationForm") RegistrationForm form, final BindingResult binding, Model model) throws Exception {
		registrationService.addForm(form);
		if (binding.hasErrors() || !registrationService.isValid(binding)) {
			return "welcome";
		}
		
		// Auto login after registration
		((IExtranetUserContext) getContext()).loginUser(registrationService.createFromForm());
		//Render success registration message
		model.addAttribute("message", MessageUtils.getSimpleMessage("/registration.txt"));
		return "info/success";
	}

	@GetMapping(value = "/sign-up")
	public String getSignUp(Model model) {
		return "redirect:/";
	}

	/**
	 * Check login form and login user.
	 * 
	 * @param form
	 * @param binding
	 * @return
	 */
	@PostMapping(value = "/sign-up")
	public String postSignUp(@Valid @ModelAttribute("loginForm") LoginForm form, final BindingResult binding) {
		loginService.addForm(form);
		if (binding.hasErrors() || !loginService.isValid(binding)) {
			return "welcome";
		}
		loginService.handleRememberMe();
		((IExtranetUserContext) getContext()).loginUser(loginService.getUser());
		return "redirect:/";
	}

}
