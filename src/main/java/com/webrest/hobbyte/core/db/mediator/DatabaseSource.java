/**
 * 
 */
package com.webrest.hobbyte.core.db.mediator;

import java.util.Properties;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.webrest.hobbyte.core.file.properties.PropertiesFacade;
import com.webrest.hobbyte.core.file.properties.PropertyService;

/**
 * Object for automatic fill {@link DriverManagerDataSource} by values from
 * specified properties.
 * 
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
@Service
public class DatabaseSource extends DriverManagerDataSource {

	private PropertyService propertyService;

	private static final String DEFAULT_PROPERTIES = "application";

	/**
	 * Create instance of {@link DatabaseSource} with
	 * <code>application.properties</code>
	 */
	public DatabaseSource() {
		this(DEFAULT_PROPERTIES);
	}

	/**
	 * Create instance of {@link DatabaseSource} with parameters from passed name of
	 * property file. </br>
	 * If passed propertiesName is null or empty, instance'll be create with
	 * {@value #DEFAULT_PROPERTIES} properties.
	 * 
	 * @param propertiesName
	 */
	public DatabaseSource(String propertiesName) {
		propertyService = PropertiesFacade.get(StringUtils.isEmpty(propertiesName) ? DEFAULT_PROPERTIES : propertiesName);
		init();
	}

	/**
	 * Fill expanded {@link DriverManagerDataSource} by values from properties.
	 * </br>
	 * Neccessary fields: </br>
	 * <ul>
	 * <li>Database url</li>
	 * <li>Username</li>
	 * <li>Password</li>
	 * <li>Driver class name</li>
	 * </ul>
	 * 
	 */
	protected void init() {
		Properties props = propertyService.getProperties();
		setUrl(props.getProperty("spring.datasource.url"));
		setUsername(props.getProperty("spring.datasource.username"));
		setPassword(props.getProperty("spring.datasource.password"));
		setDriverClassName(props.getProperty("spring.datasource.driver-class-name"));
	}

}
