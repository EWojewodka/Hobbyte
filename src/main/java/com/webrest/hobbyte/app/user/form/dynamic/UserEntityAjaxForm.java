package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.GenericAjaxDynamicForm;
import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

public abstract class UserEntityAjaxForm<T> extends GenericAjaxDynamicForm<T> {

	@Autowired
	private ExtranetUserDao userDao;

	private ExtranetUser user;

	public T run() {
		return ExceptionStream.handle(e -> {
			return handleException(e);
		}).call(() -> {
			valid();
			return process(getContext());
		}).get();
	}
	
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

	@Override
	protected T handleException(Exception e) {
		return null;
	}

}
