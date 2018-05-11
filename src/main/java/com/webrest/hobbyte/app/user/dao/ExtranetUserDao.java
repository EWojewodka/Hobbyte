/**
 * 
 */
package com.webrest.hobbyte.app.user.dao;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webrest.hobbyte.app.user.listeners.ExtranetUserDependencyListener;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dao.GenericDao;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
@Component
public class ExtranetUserDao extends GenericDao<ExtranetUser> {

	@Autowired
	private ExtranetUserDependencyListener dependencyListener;
	
	
	@PostConstruct
	public void init() {
		addListener(dependencyListener);
	}
	
	public ExtranetUser findByLoginOrEmail(String loginOrEmail) {
		ExtranetUser result = findByEmail(loginOrEmail);
		if(result == null)
			result = findByLogin(loginOrEmail);
		return result;
	}
	
	public ExtranetUser findByLogin(String login) {
		ExtranetUser findBy = findBy("login", login);
		return findBy;
	}
	
	public ExtranetUser findByEmail(String email) {
		return findBy("email", email);
	}

}
