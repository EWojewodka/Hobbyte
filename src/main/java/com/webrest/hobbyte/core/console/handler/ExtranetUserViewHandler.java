/**
 * 
 */
package com.webrest.hobbyte.core.console.handler;

import org.springframework.ui.Model;

import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.exception.RedirectException;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;

/**
 * @author Emil Wojewódka
 *
 * @since 8 kwi 2018
 */
public class ExtranetUserViewHandler extends ConsoleHandler {

	public ExtranetUserViewHandler(IConsole console) {
		super(console);
	}

	@Override
	public void handle(ExtranetUserContext context, Model model) throws Exception {
		super.handle(context, model);
		throw new RedirectException(context, "/sys/console?console-id=users-new");
	}

	@Override
	public Class<?>[] getDependencies() {
		return new Class[] { ExtranetUserDao.class };
	}

}
