/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
public abstract class UserAjaxForm extends AjaxDynamicForm {

	private ExtranetUser user;

	public UserAjaxForm(DependencyResolver dependencyResolver) {
		super(dependencyResolver);
	}

	@Override
	public Class<?>[] getDependencies() {
		return ArrayUtils.addAll(super.getDependencies(), new Class<?>[] {ExtranetUserDao.class});
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
		AjaxAsserts.notNull(user, "User not logged.");
	}

	public ExtranetUser getUser() {
		return user;
	}

	public ExtranetUserDao getUserDao() {
		return getDependency(ExtranetUserDao.class);
	}

}
