/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.exception.AjaxMessageException;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
public abstract class UserAjaxForm extends AjaxDynamicForm {

	private ExtranetUser user;

	private ExtranetUserDao userDao;

	public UserAjaxForm(ExtranetUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Throw exception if session user is not logged TODO: Bad implementation. This
	 * method should be invoked everytime without manual invoke in
	 * {@link #process(HttpServletRequest)} method.
	 * 
	 * @param request
	 * @throws AjaxMessageException
	 */
	public void valid(HttpServletRequest request) throws AjaxMessageException {
		user = ExtranetUserUtils.getUser(request);
		// Only logged users could change they email. Obviously.
		if (user == null)
			throw new AjaxMessageException("User not logged.", HttpServletResponse.SC_UNAUTHORIZED);
	}

	public ExtranetUser getUser() {
		return user;
	}

	public ExtranetUserDao getUserDao() {
		return userDao;
	}

}
