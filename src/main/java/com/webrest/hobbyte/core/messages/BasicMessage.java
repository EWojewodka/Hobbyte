package com.webrest.hobbyte.core.messages;

import java.util.HashMap;
import java.util.Map;

public class BasicMessage implements IMessage {

	private Map<String, Object> params = new HashMap<>();

	private String templateName;
	
	public BasicMessage(String templateName) {
		this.templateName = templateName;
	}
	
	@Override
	public Map<String, Object> getParams() {
		return params;
	}

	@Override
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	@Override
	public String getTemplate() {
		return "templates/messages" + templateName;
	}

}
