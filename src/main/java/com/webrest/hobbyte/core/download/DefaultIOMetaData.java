/**
 * 
 */
package com.webrest.hobbyte.core.download;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.http.HttpMethod;

import com.webrest.hobbyte.core.utils.FileUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 13 cze 2018
 */
public class DefaultIOMetaData implements IOMetaData {

	private URL url;

	private File destinationFile;

	private HttpMethod httpMethod = HttpMethod.GET;

	public DefaultIOMetaData(String urlPath, String tmpDirName, String fileName) throws MalformedURLException {
		this.destinationFile = new File(FileUtils.getTemporaryFile(tmpDirName, true), fileName);
		this.url = new URL(urlPath);
	}

	@Override
	public URL getURL() {
		return url;
	}

	@Override
	public File getDestinationFile() {
		return destinationFile;
	}

	@Override
	public HttpMethod getMethod() {
		return httpMethod;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public void setFile(File destinationFile) {
		this.destinationFile = destinationFile;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

}
