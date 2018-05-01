/**
 * 
 */
package com.webrest.hobbyte.app.posts.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.upload.cdn.CloudinaryUploader;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.platform.PlatformUtils;
import com.webrest.hobbyte.core.utils.FileUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 22 kwi 2018
 */
@Service
public class PostEntryPublisher {

	@Autowired
	private PostEntryDao postEntryDao;

	@Autowired
	private CloudinaryUploader imageUploader;

	/**
	 * Publish post entry and media as a image, video, etc.
	 * 
	 * @param user
	 * @param request
	 * @throws Exception
	 */
	public void publish(ExtranetUser user, HttpServletRequest request) throws Exception {
		Runnable runnable = () -> {
			
		};
		runnable.run();
		// return String.valueOf(upload.get("secure_url"));
	}

	private void publishImage(PostEntry entry, HttpServletRequest request) throws Exception {
		Part photo = request.getPart("photo");
		String name = photo.getSubmittedFileName();
		File dest = new File(PlatformUtils.TMP_DIR, "tmp_" + name);

		// Write file as a temporary file.
		if (!FileUtils.writeFile(photo.getInputStream(), dest)) {
			throw new Exception("Cannot copy file to (" + dest + ")");
		}
		Map<String, Object> upload = imageUploader.upload(dest);
	}
	

}
