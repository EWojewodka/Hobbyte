package com.webrest.hobbyte.core.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;

import com.webrest.hobbyte.core.logger.LoggerFactory;

public class FileUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger();

	public static final File ROOT_FILE = getRootPath();

	public static final String TMP_DIR_NAME = "hobbyte_tmp";

	public static final File TMP_DIR = new File(TMP_DIR_NAME);

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

	public static File getMailTemplate(String name) {
		return getFile("/templates/mails/content/" + name + ".mail");
	}

	public static void close(Closeable... closeables) {
		if (closeables == null || closeables.length == 0)
			return;
		for (Closeable c : closeables)
			close(c);
	}

	public static void close(Closeable closeable) {
		if (closeable != null)
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Return file extension from {@link File#getName()} and
	 * {@link #getExtension(String)} or if file is {@code file} is null return
	 * {@link StringUtils#EMPTY_STRING}
	 * 
	 * @param file
	 * @return
	 */
	public static String getExtension(File file) {
		if (file == null)
			return StringUtils.EMPTY_STRING;
		return getExtension(file.getName());
	}

	/**
	 * Return extension from passed string parameter. If null or there are no dot
	 * {@link Character} return {@link StringUtils#EMPTY_STRING}
	 * 
	 * @param string
	 * @return
	 */
	public static String getExtension(String string) {
		if (StringUtils.isEmpty(string))
			return StringUtils.EMPTY_STRING;

		int dotIndexOf = string.lastIndexOf('.');
		if (dotIndexOf == -1)
			return StringUtils.EMPTY_STRING;

		return string.substring(++dotIndexOf, string.length());
	}

	/**
	 * Write {@link InputStream} into new {@link File} with fileName name in
	 * parentFile folder. </br>
	 * <i>Passed {@link InputStream} is close after write! </i>
	 * 
	 * @param is
	 * @param parentFile
	 * @param fileName
	 * @return
	 */
	public static boolean writeFile(InputStream is, File parentFile, String fileName) {
		return writeFile(is, new File(parentFile, fileName));
	}

	/**
	 * Write an {@link InputStream} to dest file.
	 * 
	 * @see #writeFile(InputStream, File, String)
	 * @param is
	 * @param dest
	 * @return
	 */
	public static boolean writeFile(InputStream is, File dest) {
		if (is == null || dest == null)
			return false;

		OutputStream os = null;

		try {
			byte[] buffer = new byte[is.available()];
			is.read(buffer);

			os = new FileOutputStream(dest);
			os.write(buffer);
			return true;
		} catch (Exception e) {
			LOGGER.info("Cannot write InputStream to " + dest);
			e.printStackTrace();
		} finally {
			close(os, is);
		}
		return false;
	}

	/**
	 * Return file from {@link #TMP_DIR} directory
	 * 
	 * @param name
	 * @return
	 */
	public static File getTemporaryFile(String name) {
		return new File(TMP_DIR, name);
	}

}
