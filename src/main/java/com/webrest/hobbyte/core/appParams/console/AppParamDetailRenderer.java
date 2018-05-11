package com.webrest.hobbyte.core.appParams.console;

import com.webrest.hobbyte.core.appParams.AppParam;
import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.details.ParamConsole;
import com.webrest.hobbyte.core.console.render.DBOConsoleRenderer;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class AppParamDetailRenderer extends DBOConsoleRenderer<AppParam>{

	public AppParamDetailRenderer(DependencyResolver resolver, IConsole console) {
		super(resolver, console);
	}

	@Override
	protected CriteriaFilter getCriteriaFilter() {
		CriteriaFilter cf = super.getCriteriaFilter();
		cf.addWhere("group", ((ParamConsole)getConsole()).getGroup());
		return cf;
	}

}
