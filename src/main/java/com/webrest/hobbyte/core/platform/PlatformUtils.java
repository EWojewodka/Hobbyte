/**
 * 
 */
package com.webrest.hobbyte.core.platform;

import org.springframework.core.env.Environment;

/**
 * @author Emil Wojewódka
 *
 * @since 10 mar 2018
 */
public class PlatformUtils {

	public static boolean isDevelopment(Environment environment) {
		return isActiveMode(environment, AvailablePlatformProfiles.DEVELOPMENT);
	}

	public static boolean isActiveMode(Environment environment, AvailablePlatformProfiles profile) {
		String[] activeProfiles = environment.getActiveProfiles();
		for (String code : activeProfiles) {
			if (AvailablePlatformProfiles.findByCode(code) == profile)
				return true;
		}
		return false;
	}

	/**
	 * @return {@code true} if platform is running from executable jar.
	 */
	public static boolean isRunningFromJar() {
		String className = PlatformUtils.class.getName().replace('.', '/');
		String classJar = PlatformUtils.class.getResource("/" + className + ".class").toString();
		return classJar.startsWith("jar:");
	}

}
