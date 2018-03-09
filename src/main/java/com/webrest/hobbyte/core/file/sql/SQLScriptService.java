/**
 * 
 */
package com.webrest.hobbyte.core.file.sql;

import java.io.File;

import com.webrest.hobbyte.core.file.FileService;

/**
 * Service for SQL script files.
 * 
 * @see FileServiceFactory#getService(com.webrest.hobbyte.core.file.FileServiceFactory.AvailableFileService, File)
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
public class SQLScriptService extends FileService {

	/**
	 * @param file
	 */
	public SQLScriptService(File file) {
		super(file);
	}

	@Override
	protected String getCommentRegex() {
		return "\\/\\*.*?\\*\\/|--.*?\\n";
	}

	
}
