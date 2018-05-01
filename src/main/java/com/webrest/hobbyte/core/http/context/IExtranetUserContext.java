package com.webrest.hobbyte.core.http.context;

import javax.servlet.http.HttpSession;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.IHttpContext;

/**
 * Interface for handle extranet user action, eg. check current session contains
 * user.
 * 
 * @author Emil Wojewódka
 *
 * @since 9 mar 2018
 */
public interface IExtranetUserContext extends IHttpContext {

	/**
	 * Return true if {@link #getSession()} contains user object.
	 * 
	 * @return
	 */
	boolean isUserLogged();

	/**
	 * Return object from session - attribute name
	 * {@link ExtranetUserUtils#USER_SESSION_NAME}
	 * 
	 * @return
	 */
	ExtranetUser getUser();

	/**
	 * Add passed user to session from {@link #getSession()}
	 * 
	 * @param user
	 */
	void loginUser(ExtranetUser user);

	/**
	 * Remove {@link ExtranetUser} from {@link HttpSession}.
	 * 
	 * @see ExtranetUserUtils#logoutUser(HttpSession)
	 */
	void logoutUser();

}
