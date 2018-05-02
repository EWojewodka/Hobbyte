package com.webrest.hobbyte.core.model.json;

import org.json.JSONObject;

public interface JSONable {

	JSONObject getAsJSON();
	
	String getJSONAsString();
	
}
