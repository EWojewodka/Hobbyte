/**
 * 
 */
package com.webrest.hobbyte.core.platform;

import java.io.File;

import org.slf4j.Logger;
import org.springframework.core.env.Environment;

import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.utils.FileUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 10 mar 2018
 */
public class PlatformUtils {

	public static final Logger LOGGER = LoggerFactory.getLogger();

	static {
		createTmpDir();
	}

	/* Create directory for temporary files. Delete if exists on start. */
	private static void createTmpDir() {
		File file = new File(FileUtils.TMP_DIR_NAME);
		if (file.exists()) {
			file.delete();
			LOGGER.info("Delete exists {} temporary file directory", file.getAbsolutePath());
		}
		if (file.mkdir())
			LOGGER.info("Create {} temporary file directory", file.getAbsolutePath());
		else
			LOGGER.info("Cannot create {} temporary file directory", file.getAbsolutePath());
	}

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
