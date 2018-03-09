package com.webrest.hobbyte.core.messages;

import java.util.Map;

public interface IMessage {

	String getTemplate();

	Map<String, Object> getParams();

	void setParams(Map<String, Object> params);
}
