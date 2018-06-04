/**
 * 
 */
package com.webrest.hobbyte.app.base;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.net.HttpHeaders;
import com.webrest.hobbyte.core.exception.RedirectException;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.http.controllers.BaseController;

/**
 * Controller for handling basic request on main page - e.g. show board or
 * welcome page, logout, etc.
 * 
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
@Controller
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class IndexController extends BaseController {

	@RequestMapping(value = "/")
	public String index(Model model) throws RedirectException {
		if (getContext().isUserLogged())
			return "user/board";

		return "welcome";
	}

	@GetMapping(value = "/logout")
	public String getLogout() {
		ExtranetUserContext context = getContext();
		if (!context.isUserLogged())
			return "redirect:" + context.getRequest().getHeader(HttpHeaders.REFERER);

		context.logoutUser();
		return "redirect:/";
	}

}
