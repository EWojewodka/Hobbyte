/**
 * 
 */
package com.webrest.hobbyte.app.seeds.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserStatus;
import com.webrest.hobbyte.core.seed.AbstractSeed;

/**
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
@Service
public class AnnonymouseUserSeed extends AbstractSeed{
	
	@Autowired
	private ExtranetUserDao userDao;

	public AnnonymouseUserSeed() {
		super("2018-06-14");
	}
	
	@Override
	public boolean needExecute() {
		return userDao.findByLogin("Annonymouse") == null;
	}

	@Override
	public SeedExecuteResult execute() {
		ExtranetUser user = new ExtranetUser();
		user.setLogin("Annonymouse");
		user.setEmail("annon@hobbyte.com");
		user.setPassword("AnnonPassword");
		user.setStatus(ExtranetUserStatus.NOT_ACTIVE);
		user.setName("Annony");
		user.setLastname("mouse");
		userDao.save(user);
		return SeedExecuteResult.SUCCESS;
	}

}
