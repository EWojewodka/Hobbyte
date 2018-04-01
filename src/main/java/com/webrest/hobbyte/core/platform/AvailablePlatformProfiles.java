/**
 * 
 */
package com.webrest.hobbyte.core.platform;

import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithCode;

/**
 * @author Emil Wojewódka
 *
 * @since 10 mar 2018
 */
public enum AvailablePlatformProfiles implements WithCode {
	DEVELOPMENT("dev"),
	/**
	 * 
	 */
	TEST("test"),
	/**
	 * 
	 */
	PRODUCTION("prod");

	private String code;

	/**
	 * @param code
	 */
	private AvailablePlatformProfiles(String code) {
		this.code = code;
	}

	public static final AvailablePlatformProfiles findByCode(String code) {
		return EnumUtils.findByCode(AvailablePlatformProfiles.class, code);
	}

	/**
	 * Return default profile if profile with >>code<< is not found.
	 * 
	 * @param code
	 * @param defaultProfile
	 * @return
	 */
	public static final AvailablePlatformProfiles findByCode(String code, AvailablePlatformProfiles defaultProfile) {
		AvailablePlatformProfiles result = findByCode(code);
		return result == null ? defaultProfile : result;
	}

	public String getCode() {
		return code;
	}

}
