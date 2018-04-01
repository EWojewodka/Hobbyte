/**
 * 
 */
package com.webrest.hobbyte.core.profiler;

import com.webrest.hobbyte.core.platform.AvailablePlatformProfiles;

/**
 * @author Emil Wojewódka
 *
 * @since 30 mar 2018
 */
public interface IEnvironmentProfiler {

	/**
	 * @return current profile
	 */
	AvailablePlatformProfiles getProfiles();

	/**
	 * Set new environment profile.
	 * 
	 * @param profile
	 */
	void setProfile(AvailablePlatformProfiles profile);
}
