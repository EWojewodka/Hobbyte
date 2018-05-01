package com.webrest.hobbyte.app.upload.cdn;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.webrest.hobbyte.core.file.properties.EnvironmentProperties;
import com.webrest.hobbyte.core.upload.AbstractUploader;

@Service(value = "cloudinary")
@Scope("singleton")
public class CloudinaryUploader extends AbstractUploader<Map<String, Object>>{

	@Autowired
	private EnvironmentProperties properties;
	
	private Cloudinary cloudinaryService;

	@PostConstruct
	private void init() {
		Map<String, String> cloudinaryAuthMap = new HashMap<>();
		cloudinaryAuthMap.put("cloud_name", properties.getCdnName());
		cloudinaryAuthMap.put("api_key", properties.getCdnApkiKey());
		cloudinaryAuthMap.put("api_secret", properties.getCdnSecretKey());
		this.cloudinaryService = new Cloudinary(cloudinaryAuthMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> upload(File file, Map<String, String> params) throws Exception {
		Uploader uploader = cloudinaryService.uploader();
		return uploader.upload(file, params);
	}

	public Cloudinary getService() {
		return cloudinaryService;
	}

}
