/**
 * 
 */
package com.webrest.hobbyte.core.file.properties;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

import com.webrest.hobbyte.core.exception.SilentRuntimeException;


/**
 * Class which is used if platform is running from *.jar file. </br>
 * 
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public class PropertyJarService extends PropertyService {

	PropertyJarService(String propertiesName) {
		super(propertiesName, propertyName2Resource(propertiesName));
	}

	/**
	 * Return properties as a {@link InputStream} - in jar we cannot get file from
	 * inside them.
	 * 
	 * @param propertiesName
	 * @return
	 */
	private static InputStream propertyName2Resource(String propertiesName) {
		if (!propertiesName.endsWith(".properties"))
			propertiesName += ".properties";
		ClassPathResource cpr = new ClassPathResource(propertiesName);
		try {
			return cpr.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new SilentRuntimeException(String.format("Cannot get properties from jar for (%s)", propertiesName));
	}

}
