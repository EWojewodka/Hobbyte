/**
 * 
 */
package com.webrest.hobbyte.app.posts.form;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.upload.cdn.CloudinaryUploader;
import com.webrest.hobbyte.app.user.form.dynamic.UserEntityAjaxForm;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.FileUtils;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PostEntryAjax extends UserEntityAjaxForm<PostEntry> {

	private static final String[] AVAILABLE_PHOTO_EXTENSION = { "jpg", "jpeg", "png", "gif" };

	private static final String WRONG_EXTENSION_MSG = String.format(
			"Cannot upload file. Wrong fil extension - only %s are available",
			StringUtils.toGenericString(AVAILABLE_PHOTO_EXTENSION, ","));

	@Autowired
	private PostEntryDao postEntryDao;

	@Autowired
	private CloudinaryUploader uploader;

	@Override
	protected PostEntry process(IExtranetUserContext userContext) throws Exception {
		valid();

		String content = getParameter("content");
		String imageUrl = uploadImage(userContext.getRequest());
		// If there is no a image or text - throw exception. Zombie post are not nice.
		AjaxAsserts.notEmpty("Post entry content cannot be empty!", content, imageUrl);

		PostEntry postEntry = new PostEntry(getUser());
		postEntry.setImageUrl(imageUrl);
		postEntry.setContent(content);

		postEntryDao.save(postEntry);
		return postEntry;
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
		Map<String, Object> upload = uploader.upload(dest);

		// Delete tmp file.
		dest.delete();
		return String.valueOf(upload.get("secure_url"));
	}

	@Override
	public String getCode() {
		return "new-post-ajax";
	}

}
