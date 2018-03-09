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

	public static void close(Closeable closeable) {
		if (closeable != null)
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
