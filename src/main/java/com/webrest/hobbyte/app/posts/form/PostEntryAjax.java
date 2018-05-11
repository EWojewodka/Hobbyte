/**
 * 
 */
package com.webrest.hobbyte.app.posts.form;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONObject;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.upload.cdn.CloudinaryUploader;
import com.webrest.hobbyte.app.user.form.dynamic.UserAjaxForm;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.FileUtils;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
public class PostEntryAjax extends UserAjaxForm {

	private static final String[] AVAILABLE_PHOTO_EXTENSION = { "jpg", "jpeg", "png", "gif" };

	private static final String WRONG_EXTENSION_MSG = String.format(
			"Cannot upload file. Wrong fil extension - only %s are available",
			StringUtils.toGenericString(AVAILABLE_PHOTO_EXTENSION, ","));

	public PostEntryAjax(DependencyResolver dependencyResolver) {
		super(dependencyResolver);
	}
	
	@Override
	public Class<?>[] getDependencies() {
		return ArrayUtils.addAll(super.getDependencies(), new Class<?>[] {PostEntryDao.class, CloudinaryUploader.class});
	}

	@Override
	protected JSONObject process(HttpServletRequest request) throws Exception {
		valid(request);

		String content = request.getParameter("content");
		String imageUrl = uploadImage(request);
		// If there is no a image or text - throw exception. Zombie post are not nice.
		AjaxAsserts.notEmpty("Post entry content cannot be empty!", content, imageUrl);

		PostEntry postEntry = new PostEntry(getUser());
		postEntry.setImageUrl(imageUrl);
		postEntry.setContent(content);

		getDependency(PostEntryDao.class).save(postEntry);

		JSONObject result = new JSONObject();
		addMessage(result, "Your post is published!");
		return result;
	}

	/**
	 * Process and upload file from ajax request. It's optional, so if there is on
	 * attachments in request, return null - in otherwise return string url to
	 * upload destination. Now we use {@link CloudinaryUploader} for uploading a
	 * resources.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String uploadImage(HttpServletRequest request) throws Exception {
		Part photo = request.getPart("photo");
		String fileName = photo.getSubmittedFileName();
		if (photo == null || StringUtils.isEmpty(fileName))
			return null;
		String extension = FileUtils.getExtension(fileName);

		AjaxAsserts.assertTrue(StringUtils.contains(AVAILABLE_PHOTO_EXTENSION, extension), WRONG_EXTENSION_MSG);

		File dest = FileUtils.getTemporaryFile("tmp_" + fileName);
		// Write file as a temporary file.
		FileUtils.writeFile(photo.getInputStream(), dest);
		Map<String, Object> upload = getDependency(CloudinaryUploader.class).upload(dest);

		// Delete tmp file.
		dest.delete();
		return String.valueOf(upload.get("secure_url"));
	}

	@Override
	public String getCode() {
		return "new-post-ajax";
	}

}
