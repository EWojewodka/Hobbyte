/**
 * 
 */
package com.webrest.hobbyte.core.profiler;

import org.slf4j.Logger;

import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.platform.AvailablePlatformProfiles;

/**
 * Class which is reponsible for looking for a platform start profiles. In basic
 * for set specified profile add </br>
 * <code>-Dprofile.active=
 * {@link AvailablePlatformProfiles#getCode()} </code> to JVM. If there is no
 * specified any profile - we start as a
 * {@link AvailablePlatformProfiles#DEVELOPMENT} </br>
 * 
 * Currently we use a {@code Spring Framework} so it's only mediator for it. But
 * in future maybe we'll use other framework so - I did it.
 * 
 * @author Emil Wojewódka
 * @since 30 mar 2018
 */
public class EnvironmentProfiler implements IEnvironmentProfiler {

	private static final Logger LOGGER = LoggerFactory.getLogger();

	/**
	 * Argument name which contains active profile in JVM.
	 */
	private static final String PROFILE_ARGUMENT_NAME = "profile.active";

	/**
	 * Argument name for spring framework to set profile.
	 */
	private static final String SPRING_PROFILE_ARGUMENT_NAME = "spring.profiles.active";

	private String profileCode;

	private AvailablePlatformProfiles profile;

	/**
	 * Create instance of environment profiler.
	 */
	public EnvironmentProfiler() {
		this.profileCode = System.getProperty(PROFILE_ARGUMENT_NAME);
		this.profile = AvailablePlatformProfiles.findByCode(profileCode, AvailablePlatformProfiles.DEVELOPMENT);
	}

	/**
	 * Method which should be execute before platform configuration. At all this
	 * invoke only {@link #setProfile(AvailablePlatformProfiles)} by
	 * {@value #PROFILE_ARGUMENT_NAME} value from {@link System#getProperty(String)}
	 */
	public void setOnStart() {
		setProfile(profile);
	}

	@Override
	public AvailablePlatformProfiles getProfiles() {
		return profile;
	}

	/**
	 * Set profile code in System property for
	 * {@value #SPRING_PROFILE_ARGUMENT_NAME}
	 */
	@Override
	public void setProfile(AvailablePlatformProfiles profile) {
		this.profile = profile;
		System.setProperty(SPRING_PROFILE_ARGUMENT_NAME, profile.getCode());
		LOGGER.info(String.format("Environment profile is change to: (%s)", profile.getCode()));
	}

}
