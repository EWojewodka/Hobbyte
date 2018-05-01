/**
 * 
 */
package com.webrest.hobbyte.app.posts.form;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.upload.cdn.CloudinaryUploader;
import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.platform.PlatformUtils;
import com.webrest.hobbyte.core.utils.FileUtils;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
public class PostEntryAjax extends AjaxDynamicForm {

	private static final String[] AVAILABLE_PHOTO_EXTENSION = { "jpg", "jpeg", "png", "gif" };

	private static final String WRONG_EXTENSION_MSG = String.format(
			"Cannot upload file. Wrong fil extension - only %s are available",
			StringUtils.toGenericString(AVAILABLE_PHOTO_EXTENSION, ","));

	public PostEntryAjax(DependencyResolver dependencyResolver) {
		super(dependencyResolver);
	}

	@Override
	protected JSONObject process(HttpServletRequest request) throws Exception {
		ExtranetUser user = ExtranetUserUtils.getUser(request);
		if (user == null)
			throw new AjaxMessageException("You must be logged for add new post.", HttpServletResponse.SC_BAD_REQUEST);

		String content = request.getParameter("content");
		if (StringUtils.isEmpty(content))
			throw new AjaxMessageException("Post entry content cannot be empty!", HttpServletResponse.SC_BAD_REQUEST);

		PostEntry postEntry = new PostEntry(user);
		postEntry.setImageUrl(uploadImage(request));
		postEntry.setContent(content);

		getDependency(PostEntryDao.class).save(postEntry);

		JSONObject result = new JSONObject();
		addMessage(result, "Your post is published!");
		return result;
	}

	private String uploadImage(HttpServletRequest request) throws Exception {
		Part photo = request.getPart("photo");
		if (photo == null)
			return null;
		String name = photo.getSubmittedFileName();
		String extension = FileUtils.getExtension(name);
		if (!StringUtils.contains(AVAILABLE_PHOTO_EXTENSION, extension)) {
			throw new AjaxMessageException(WRONG_EXTENSION_MSG, HttpServletResponse.SC_BAD_REQUEST);
		}
		File dest = new File(PlatformUtils.TMP_DIR, "tmp_" + name);
		// Write file as a temporary file.
		FileUtils.writeFile(photo.getInputStream(), dest);
		CloudinaryUploader uploader = getDependency(CloudinaryUploader.class);
		Map<String, Object> upload = uploader.upload(dest);
		// Delete tmp file.
		dest.delete();
		return String.valueOf(upload.get("secure_url"));
	}

	@Override
	public String getCode() {
		return "new-post-ajax";
	}

	@Override
	public Class<?>[] getDependencies() {
		return new Class[] { PostEntryDao.class, CloudinaryUploader.class };
	}

}
