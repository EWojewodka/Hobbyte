package com.webrest.hobbyte.core.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public class FileUtils {

	public static final File ROOT_FILE = getRootPath();

	private static final File getRootPath() {
		return new File(FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile());
	}

	public static File getFile(String relativePath) {
		return new File(ROOT_FILE, relativePath);
	}

	/**
	 * Return true is template /templates/error/{errorCode}.html exists.
	 * 
	 * @param errorCode
	 * @return
	 */
	public static boolean existsErrorPage(int errorCode) {
		return FileUtils.class.getClassLoader().getResource("templates/error/" + errorCode + ".html") != null;
	}

	public static void close(Closeable closeable) {
		if (closeable != null)
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
