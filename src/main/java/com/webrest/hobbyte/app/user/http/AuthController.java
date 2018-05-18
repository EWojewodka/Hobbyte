package com.webrest.hobbyte.app.user.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webrest.hobbyte.app.user.form.dynamic.LoginAjaxForm;
import com.webrest.hobbyte.app.user.form.dynamic.RegistrationAjaxForm;
import com.webrest.hobbyte.core.http.controllers.BaseController;

@Controller
@RequestMapping(value = "/auth")
public class AuthController extends BaseController {

	@Autowired
	private RegistrationAjaxForm signInForm;

	@Autowired
	private LoginAjaxForm signUpForm;

	@GetMapping(value = "/sign-in")
	public String getSignIn(Model model) {
		return "redirect:/";
	}

	@PostMapping(value = "/sign-in")
	@ResponseBody
	public String postSignIn() throws Exception {
		return signInForm.run();
	}

	@GetMapping(value = "/sign-up")
	public String getSignUp(Model model) {
		return "redirect:/";
	}

	@PostMapping(value = "/sign-up")
	@ResponseBody
	public String postSignUp() {
		return signUpForm.run();
	}

}