/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.utils.AjaxAsserts;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
public abstract class UserAjaxForm extends AjaxDynamicForm {

	@Autowired
	private ExtranetUserDao userDao;

	private ExtranetUser user;

	/**
	 * Throw exception if session user is not logged TODO: Bad implementation. This
	 * method should be invoked everytime without manual invoke in
	 * {@link #process(HttpServletRequest)} method.
	 * 
	 * @param request
	 * @throws AjaxMessageException
	 */
	public void valid() throws AjaxMessageException {
		user = ExtranetUserUtils.getUser(getContext());
		// Only logged users could change they email. Obviously.
		AjaxAsserts.notNull(user, "User not logged.");
	}

	public ExtranetUser getUser() {
		return user;
	}

	public ExtranetUserDao getUserDao() {
		return userDao;
	}

}
