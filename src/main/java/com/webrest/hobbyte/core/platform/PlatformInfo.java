/**
 * 
 */
package com.webrest.hobbyte.core.platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.file.properties.EnvironmentProperties;
import com.webrest.hobbyte.core.file.properties.PropertiesGetter;

/**
 * @author Emil Wojewódka
 *
 * @since 30 mar 2018
 */
@Service
public class PlatformInfo {

	
	@Autowired
	private ConfigurableEnvironment environment;

	@Autowired
	private EnvironmentProperties springProperties;
	
	public boolean isActiveMode(AvailablePlatformProfiles profile) {
		return PlatformUtils.isActiveMode(environment, profile);
	}

	public boolean isDevelopment() {
		return PlatformUtils.isDevelopment(environment);
	}
	
	public PropertiesGetter getUsingPropertiesGetter() {
		return new PropertiesGetter(springProperties);
	}
	
	public EnvironmentProperties getUsingProperties() {
		return springProperties;
	}

}
