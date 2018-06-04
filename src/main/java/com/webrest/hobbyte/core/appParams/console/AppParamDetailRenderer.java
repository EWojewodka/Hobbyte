package com.webrest.hobbyte.core.appParams.console;

import com.webrest.hobbyte.core.appParams.AppParam;
import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.details.ParamConsole;
import com.webrest.hobbyte.core.console.render.DBOConsoleRenderer;
import com.webrest.hobbyte.core.console.render.ToolbarButton;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class AppParamDetailRenderer extends DBOConsoleRenderer<AppParam>{

	public AppParamDetailRenderer(DependencyResolver resolver, IConsole console) {
		super(resolver, console);
		initButtons();
	}
	
	private void initButtons() {
		ToolbarButton addNewBtn = new ToolbarButton("add");
		addNewBtn.setCodeAction("add");
		addNewBtn.setLabel("Add new");
		addButton(addNewBtn);
		ToolbarButton saveBtn = new ToolbarButton("save");
		saveBtn.setCodeAction("save");
		saveBtn.setLabel("Save");
		addButton(saveBtn);
		
	}

	@Override
	protected CriteriaFilter getCriteriaFilter() {
		CriteriaFilter cf = super.getCriteriaFilter();
		cf.addWhere("group", ((ParamConsole)getConsole()).getGroup());
		return cf;
	}
	
	

}
