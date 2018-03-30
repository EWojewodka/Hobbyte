/**
 * 
 */
package com.webrest.hobbyte.core.file.properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.platform.AvailablePlatformProfiles;

/**
 * Class for gets currently using properties. </br>
 * <i>Note: </i>Only defined as a class field property will be execute and
 * available here!
 * 
 * @author Emil Wojewódka
 *
 * @since 30 mar 2018
 */
@Service
public class SpringProperties extends EnvironmentProperties {

	private static final long serialVersionUID = -6108674862600820134L;

	@Value(value = "${server.address}")
	private String serverHost;

	@Value(value = "${server.port}")
	private String serverPort;

	@Value(value = "${mail.host}")
	private String mailHost;

	@Value(value = "${mail.port}")
	private String mailPort;

	@Value(value = "${mail.username}")
	private String mailUsername;

	@Value(value = "${mail.password}")
	private String mailPassword;

	@Value(value = "${profile.mode}")
	private String profile;

	private SpringProperties() {
		// Only for inject
	}

	@PostConstruct
	private void init() {
		ReflectionPropertiesBuilder.build(this);
	}

	/** {@inheritDoc}} */
	public String getServerHost() {
		return serverHost;
	}

	/** {@inheritDoc}} */
	public String getServerPort() {
		return serverPort;
	}

	/** {@inheritDoc}} */
	public String getMailHost() {
		return mailHost;
	}

	/** {@inheritDoc}} */
	public String getMailPort() {
		return mailPort;
	}

	/** {@inheritDoc}} */
	public String getMailUsername() {
		return mailUsername;
	}

	/** {@inheritDoc}} */
	public String getMailPassword() {
		return mailPassword;
	}

	/** {@inheritDoc}} */
	public boolean isTest() {
		return isProfile(AvailablePlatformProfiles.TEST);
	}

	/** {@inheritDoc}} */
	public boolean isDevelopment() {
		return isProfile(AvailablePlatformProfiles.DEVELOPMENT);
	}

	/** {@inheritDoc}} */
	public boolean isProduction() {
		return isProfile(AvailablePlatformProfiles.PRODUCTION);
	}

	public boolean isProfile(AvailablePlatformProfiles profile) {
		return this.profile.equals(profile.getCode());
	}

}
