/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
public class ChangePhoneForm extends UserAjaxForm {

	public ChangePhoneForm(DependencyResolver dependencyResolver) {
		super(dependencyResolver);
	}

	@Override
	protected JSONObject process(HttpServletRequest request) throws Exception {
		valid(request);
		String phoneNumber = getParameter("phone-number");
		if (!StringUtils.isNumeric(phoneNumber))
			throw new AjaxMessageException("Phone number can contains only numeric.",
					HttpServletResponse.SC_BAD_REQUEST);
		ExtranetUser user = getUser();
		user.setPhoneNumber(phoneNumber);
		getUserDao().save(user);
		JSONObject result = new JSONObject();
		addMessage(result, "Your phone number has been changed.");
		return result;
	}

	@Override
	public String getCode() {
		return "phone";
	}

}
