package com.webrest.hobbyte.core.console.details;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.appParams.AppParam;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.StringUtils;

public class ParamConsole extends DetailsConsole{

	private String group;
	
	private boolean canAdd;
	
	public ParamConsole(Element element) throws Exception {
		super(element);
	}

	@Override
	public void init() throws Exception {
		super.init();
		this.group = getAttribute("group");
		this.canAdd = StringUtils.getAsBoolean(getAttribute("new"));
		Asserts.notEmpty(group, "Cannot create ParamConsole without group");
	}
	
	public String getGroup() {
		return group;
	}
	
	public boolean canAdd() {
		return canAdd;
	}

	@Override
	protected Class<? extends DatabaseObjectImpl> initObjectClass(String beanClassAttribute) throws Exception {
		return AppParam.class;
	}
	
}
