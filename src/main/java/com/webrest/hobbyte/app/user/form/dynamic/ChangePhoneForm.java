/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
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
	protected void process(HttpServletRequest request) throws Exception {
		valid(request);
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
