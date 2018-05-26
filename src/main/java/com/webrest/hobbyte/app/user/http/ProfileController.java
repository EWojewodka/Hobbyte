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

import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.form.dynamic.ChangeEmailForm;
import com.webrest.hobbyte.app.user.form.dynamic.ChangeLastnameForm;
import com.webrest.hobbyte.app.user.form.dynamic.ChangeNameForm;
import com.webrest.hobbyte.app.user.form.dynamic.ChangePhoneForm;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.http.controllers.BaseController;

/**
 * @author Emil Wojewódka
 *
 * @since 9 mar 2018
 */
@Controller
public class ProfileController extends BaseController {

	@Autowired
	private ExtranetUserDao userDAO;

	@Autowired
	private ChangeEmailForm emailForm;

	@Autowired
	private ChangePhoneForm phoneForm;

	@Autowired
	private ChangeNameForm nameForm;

	@Autowired
	private ChangeLastnameForm lastnameForm;

	@GetMapping(value = { "/profile/{login}", "/profile" })
	public String getProfile(@PathVariable(name = "login", required = false) String login, Model model)
			throws Exception {
		//We shouldn't looking for a user if login is empty.
		boolean isLogin = StringUtils.isEmpty(login);
		ExtranetUser user = isLogin ? null : userDAO.findByLogin(login);
		//So if login is empty (probably user is logged)
		if (user == null) {
			if (getContext().isUserLogged() && isLogin)
				user = getContext().getUser();
			else
				return "redirect:/";
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
		AjaxDynamicForm form = null;
		switch (type) {
		case "email":
			form = emailForm;
			break;
		case "phone":
			form = phoneForm;
			break;
		case "name":
			form = nameForm;
			break;
		case "lastname":
			form = lastnameForm;
			break;
		}
		return form.run();
	}

}
