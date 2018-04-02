package com.webrest.hobbyte.core.console;

import com.webrest.hobbyte.core.model.DatabaseObjectImpl;

public interface IAdminConsole {

	String getUri();
	
	String getId();
	
	Class<? extends DatabaseObjectImpl> getBeanClass();
	
	String[] getFields();
	
}
