package com.webrest.hobbyte.core.console.render;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.details.ParamConsole;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class AppParamRenderer extends ConsoleRenderer{

	public AppParamRenderer(DependencyResolver resolver, IConsole console) {
		super(resolver, console);
	}

	@Override
	protected CriteriaFilter getCriteriaFilter() {
		CriteriaFilter cf = super.getCriteriaFilter();
		cf.addWhere("group", ((ParamConsole)getConsole()).getGroup());
		return cf;
	}

}
