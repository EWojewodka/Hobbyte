/**
 * 
 */
package com.webrest.hobbyte.core.model;

import java.lang.reflect.Field;

import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

/**
 * @author Emil Wojewódka
 *
 * @since 22 kwi 2018
 */
public class DBOPropertiesService {

	private DatabaseObjectImpl dbo;

	private String propertiesFieldName;

	public DBOPropertiesService(DatabaseObjectImpl dbo, String propertiesFieldName) {
		this.dbo = dbo;
		this.propertiesFieldName = propertiesFieldName;
		init();
	}

	private void init() {
		ExceptionStream.printOnFailure().call(() -> {
			Field field = dbo.getClass().getField(propertiesFieldName);
			String text = (String) field.get(dbo);
			System.out.println(text);
		});
	}

}
