/**
 * 
 */
package com.webrest.hobbyte.app.user.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webrest.hobbyte.app.user.dao.ExtranetUserPolicyDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dao.DaoListenerImpl;

/**
 * @author Emil Wojewódka
 *
 * @since 25 mar 2018
 */
@Component
public class ExtranetUserDependencyListener extends DaoListenerImpl<ExtranetUser>{

	@Autowired
	private ExtranetUserPolicyDao policyDao;
	
	@Override
	public void afterFirstSave(ExtranetUser databaseObject){
		super.afterFirstSave(databaseObject);
		policyDao.save(databaseObject.createOrGetUserPolicy());
	}
	
}
