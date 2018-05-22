/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ChangePhoneForm extends UserAjaxForm {

	@Override
	protected void process(IExtranetUserContext context) throws Exception {
		valid(context.getRequest());

		String phoneNumber = getParameter("phone-number");
		AjaxAsserts.assertTrue(StringUtils.isNumeric(phoneNumber), "Phone number can contains only numeric.");

		ExtranetUser user = getUser();
		user.setPhoneNumber(phoneNumber);
		getUserDao().save(user);
		addMessage("Your phone number has been changed.");
	}

	@Override
	public String getCode() {
		return "phone";
	}

}
