/**
 * 
 */
package com.webrest.hobbyte.core.download;

import java.io.File;
import java.net.URL;

import org.springframework.http.HttpMethod;

/**
 * Skeleton for Input/Output data. </br>
 * Type: Data transfer object.
 * 
 * @author Emil Wojewódka
 *
 * @since 12 cze 2018
 */
public interface IOMetaData {

	/**
	 * Return {@link URL} of file which should be downloaded by service.
	 * 
	 * @return
	 */
	URL getURL();

	/**
	 * Return a {@link File} of directory where should be stored downloaded file.
	 * 
	 * @return
	 */
	File getDestinationFile();

	/**
	 * Return type of {@link HttpMethod} (e.g. POST, GET) connection with
	 * {@link #getURL()}
	 * 
	 * @return
	 */
	HttpMethod getMethod();

}
