package com.webrest.hobbyte.core.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUploader<T>  implements IUploader<T>{


	@SuppressWarnings("rawtypes")
	private static final Map EMPTY_MAP = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public T upload(File file) throws Exception {
		return (T) upload(file, EMPTY_MAP);
	}
	
}
