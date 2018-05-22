/**
 * 
 */
package com.webrest.hobbyte.app.seeds.welcomePage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.appParams.AppParam;
import com.webrest.hobbyte.core.appParams.AppParamDao;
import com.webrest.hobbyte.core.seed.AbstractSeed;

/**
 * @author Emil Wojewódka
 *
 * @since 19 maj 2018
 */
@Service
public class WelcomePageTitleSeed extends AbstractSeed {

	@Autowired
	private AppParamDao paramDao;

	public WelcomePageTitleSeed() {
		super("2018-05-20");
	}

	@Override
	public SeedExecuteResult execute() {
		AppParam appParam = new AppParam();
		appParam.setGroup("welcome_page");
		appParam.setCode("Above_registration_form");
		appParam.setValue("Make yout hobby easier!");
		paramDao.save(appParam);
		return SeedExecuteResult.SUCCESS;
	}

	@Override
	public boolean needExecute() {
		return paramDao.find("welcome_page", "Above_registration_form") == null;
	}

}
