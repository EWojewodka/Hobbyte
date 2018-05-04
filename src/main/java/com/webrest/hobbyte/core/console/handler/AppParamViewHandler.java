package com.webrest.hobbyte.core.console.handler;

import org.springframework.ui.Model;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.details.ParamConsole;
import com.webrest.hobbyte.core.console.render.AppParamRenderer;
import com.webrest.hobbyte.core.exception.RedirectException;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class AppParamViewHandler extends ConsoleHandler {

	public AppParamViewHandler(DependencyResolver resolver, IConsole console) {
		super(resolver, console);
		setRenderer(new AppParamRenderer(resolver, console));
	}
	
	@Override
	public void onAdd(ExtranetUserContext context, Model model) throws Exception {
		super.onAdd(context, model);
		throw new RedirectException(context, "/sys/console?console-id=app-param-new&group=" + ((ParamConsole)getConsole()).getGroup());
	}

}
