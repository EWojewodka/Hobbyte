package com.webrest.hobbyte.core.model.json;

import org.json.JSONObject;

/**
 * @see JsonConverter#toJson(Object)
 * @author EWojewodka
 *
 */
public interface JSONable {

	/**
	 * Interface method for covert object with implementation to json format.
	 * 
	 * @see JsonConverter
	 * @return
	 */
	JSONObject getAsJSON();

	/**
	 * Get java object in JSON format. e.g. 
	 * <pre>
	 * { "post" : {
	 * 	"id" : 124,
	 * 	"content": "foo foo FooFOO XDD",
	 * 	"author" : {
	 * 		"userId": 1,
	 *	 	"name" : "Emil"
	 * 		}
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @see #getAsJSON()
	 * @return
	 */
	String getJSONAsString();

}
