/**
 * 
 */
package com.webrest.hobbyte.core.file.properties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;

import com.webrest.hobbyte.core.file.FileService;
import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

/**
 * Instance should be init by {@link PropertiesFacade#get(String)}
 * 
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
public class PropertyService extends FileService {

	private static final Logger LOGGER = LoggerFactory.getLogger();

	private Properties props;

	private String propertiesName;

	public PropertyService(String propertiesName, InputStream is) {
		super(is);
		this.propertiesName = propertiesName;
	}
	
	/**
	 * Create instance of {@link PropertyService} based on file object.
	 * 
	 * @param propertiesName
	 */
	PropertyService(File file) {
		super(file);
		this.propertiesName = file.getName();
	}

	/**
	 * Create instance of {@link PropertyService} based on properties name.
	 * 
	 * @param propertiesName
	 */
	PropertyService(String propertiesName) {
		this(propertyName2File(propertiesName));
		this.propertiesName = propertiesName;
	}

	// Find property file by name.
	protected static File propertyName2File(String propertyName) {
		if (!propertyName.endsWith(".properties"))
			propertyName = propertyName + ".properties";
		
		final String propName = propertyName;
		return ExceptionStream.printOnFailure().call(() -> {
			return new File(PropertyService.class.getClassLoader().getResource(propName).toURI());
		}).get();
	}

	/**
	 * Initialize props object field and load them from {@link #open()}
	 */
	private void load() {
		try {
			InputStream open = open();
			props = new Properties();
			props.load(open);
		} catch (IOException exc) {
			LOGGER.info("Cannot load properties for name ({})", propertiesName);
		}
	}

	/**
	 * Return file properties.
	 * 
	 * @return
	 */
	public Properties getProperties() {
		if (props == null)
			load();
		return props;
	}

	public String getPropertiesName() {
		return propertiesName;
	}

}