/**
 * 
 */
package com.webrest.hobbyte.core.file;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
public interface IFileService {
	
	/**
	 * Read file and return content as a string
	 * @return
	 */
	String read() throws IOException;
	
	/**
	 * Open file stream.
	 * @return
	 */
	InputStream open() throws IOException;
	
	/**
	 * Close stream.
	 */
	void close() throws IOException;
	
}
