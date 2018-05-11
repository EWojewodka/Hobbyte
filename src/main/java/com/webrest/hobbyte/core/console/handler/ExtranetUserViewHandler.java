/**
 * 
 */
package com.webrest.hobbyte.core.console.handler;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.ui.Model;

import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.exception.RedirectException;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * @author Emil Wojewódka
 *
 * @since 8 kwi 2018
 */
public class ExtranetUserViewHandler extends DBOConsoleHandler {

	public ExtranetUserViewHandler(DependencyResolver resolver, IConsole console) {
		super(resolver, console);
	}

	@Override
	public Class<?>[] getDependencies() {
		return ArrayUtils.addAll(super.getDependencies(), new Class<?>[] { ExtranetUserDao.class });
	}

	@Override
	public void onAdd(ExtranetUserContext context, Model model) throws Exception {
		throw new RedirectException(context, "/sys/console?console-id=users-new");
	}

}
