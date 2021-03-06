/**
 * 
 */
package com.webrest.hobbyte.app.seeds.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserRoles;
import com.webrest.hobbyte.core.seed.AbstractSeed;

/**
 * @author Emil Wojewódka
 *
 * @since 20 maj 2018
 */
@Service
public class AdministratorSeed extends AbstractSeed {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private ExtranetUserDao userDao;

	public AdministratorSeed() {
		super("2018-05-20");
	}

	@Override
	public boolean needExecute() {
		return userDao.findByLogin("Administrator") == null && userDao.findByEmail("adm@hobbyte.com") == null;
	}

	@Override
	public SeedExecuteResult execute() {
		ExtranetUser user = new ExtranetUser();
		user.setLogin("Administrator");
		user.setEmail("adm@hobbyte");
		user.setPassword(encoder.encode("adminPassword"));
		user.setName("Emil");
		user.setLastname("Wojewódka");
		user.setRole(ExtranetUserRoles.ADMIN);
		userDao.save(user);
		return SeedExecuteResult.SUCCESS;
	}

}
