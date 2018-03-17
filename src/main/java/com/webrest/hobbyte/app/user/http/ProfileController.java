/**
 * 
 */
package com.webrest.hobbyte.app.user.http;

import javax.websocket.server.PathParam;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webrest.hobbyte.app.user.dao.ExtranetUserDAO;
import com.webrest.hobbyte.app.user.form.dynamic.UserSettingFormFactory;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.exception.response.NotFoundException;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.http.context.IHttpContext;
import com.webrest.hobbyte.core.http.controllers.BaseController;

/**
 * @author Emil Wojewódka
 *
 * @since 9 mar 2018
 */
@Controller
public class ProfileController extends BaseController {

	@Autowired
	private ExtranetUserDAO userDAO;

	@Autowired
	private IExtranetUserContext userContext;

	@Autowired
	private UserSettingFormFactory formFactory;

	@GetMapping(value = { "/profile/{login}", "/profile" })
	public String getProfile(@PathVariable(name = "login", required = false) String login, Model model)
			throws Exception {
		ExtranetUser user = userDAO.findByLogin(login);
		if (user == null) {
			if (userContext.isUserLogged() && StringUtils.isEmpty(login))
				user = userContext.getUser();
			else
				throw new NotFoundException(getContext());
		}
		model.addAttribute("userProfile", user);
		return "user/profile";
	}

	@GetMapping(value = { "/profile/settings/{type}", "/profile/settings" })
	public String getSettings(@PathVariable(name = "type", required = false) String type) {
		if (StringUtils.isEmpty(type))
			type = "account";
		return "user/settings/" + type;
	}

	/**
	 * It's rest resource. We return {@link JSONObject} as a {@link String} </br>
	 * Every dynamic form should be handled by individual logic, which should be
	 * found by form id. For example: if you use form for change an email, you
	 * should create new object which can handle it, and object's code is "email".
	 * 
	 * @param type
	 * @param formId
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = { "/profile/settings" })
	public String postSettings(@PathParam("type") String type) {
		AjaxDynamicForm form = formFactory.get(type);
		IHttpContext context = getContext();
		return form.run(context.getRequest(), context.getResponse());
	}
}
