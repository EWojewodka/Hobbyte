/**
 * 
 */
package com.webrest.hobbyte.core.file.properties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.slf4j.Logger;

import com.webrest.hobbyte.core.file.FileService;
import com.webrest.hobbyte.core.logger.LoggerFactory;

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

	PropertyService(File file) {
		super(file);
		this.propertiesName = file.getName();
	}

	/**
	 * @param file
	 */
	PropertyService(String propertiesName) {
		this(propertyName2File(propertiesName));
		this.propertiesName = propertiesName;
	}

	// Find property file by name.
	private static File propertyName2File(String propertyName) {
		if (!propertyName.endsWith(".properties"))
			propertyName = propertyName + ".properties";

		URI uri = null;
		try {
			uri = PropertyService.class.getClassLoader().getResource(propertyName).toURI();
		} catch (URISyntaxException e) {
			LOGGER.info("Cannot get properties resource for name ({})", propertyName);
			e.printStackTrace();
		}
		return new File(uri);
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