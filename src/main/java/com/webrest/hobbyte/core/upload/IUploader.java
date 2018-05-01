package com.webrest.hobbyte.core.upload;

import java.io.File;
import java.util.Map;

public interface IUploader<T> {

	/**
	 * Upload {@link File} to destination. Returned value is defined by
	 * {@code generic type}
	 * 
	 * @see #upload(File, Map)
	 * @param file
	 * @return
	 * @throws Exception
	 */
	T upload(File file) throws Exception;

	/**
	 * Upload {@link File} to destination. Returned value is defined by
	 * {@code generic type}. In param you can pass extra parameters which should be
	 * passed to a service delivery.
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	T upload(File file, Map<String, String> params) throws Exception;

}
