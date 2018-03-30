/**
 * 
 */
package com.webrest.hobbyte.core.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.webrest.hobbyte.core.file.FileService.FileServiceBuffer;

/**
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
public interface IFileService {

	/**
	 * A cache object for {@link IFileService}s implementation. It's more better
	 * than store them inside map. Non-natural {@link #equals(Object)} allow get
	 * service by {@link List#indexOf(>your service name<)} and next
	 * {@link List#get(int)}
	 */
	static final List<FileServiceBuffer> SERVICE_BUFFER = new ArrayList<>();

	/**
	 * Read file and return content as a string
	 * 
	 * @return
	 */
	String read() throws IOException;

	/**
	 * Open file stream.
	 * 
	 * @return
	 */
	InputStream open() throws IOException;

	/**
	 * Close stream.
	 */
	void close() throws IOException;

}
