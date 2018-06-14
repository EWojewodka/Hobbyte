/**
 * 
 */
package com.webrest.hobbyte.app.socialMedia.facebook;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.app.upload.cdn.CloudinaryUploader;
import com.webrest.hobbyte.app.user.form.dynamic.DefaultRegistrationSource;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.app.user.model.enums.RegistrationType;
import com.webrest.hobbyte.core.download.DefaultIOMetaData;
import com.webrest.hobbyte.core.download.ImageDownloader;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 12 cze 2018
 */
@Service("facebookRegistration")
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FacebookRegistrationSource extends DefaultRegistrationSource {

	@Autowired
	private ImageDownloader imageDownloader;

	@Autowired
	private CloudinaryUploader uploader;

	@Override
	public void validate() throws Exception {
		ExtranetUserContext context = getContext();

		String email = getParameter("email");
		String emailMsg = msgHelper.getMessage("NotEmail", context);
		AjaxAsserts.assertTrue(StringUtils.isEmail(email), emailMsg);

		AjaxAsserts.assertNull(userDao.findByLogin(email), msgHelper.getMessage("NotAvailableLogin", context));
		AjaxAsserts.assertNull(userDao.findByEmail(email), msgHelper.getMessage("NotAvailableEmail", context));
	}

	@Override
	public ExtranetUser buildUser() {
		String email = getParameter("email");

		ExtranetUser user = new ExtranetUser();
		String name = getParameter("name");
		String lastname = getParameter("lastname");

		user.setLogin(name + "-" + lastname);
		user.setEmail(email);
		user.setName(name);
		user.setLastname(lastname);
		// user.setImageUrl(getParameter("imageUrl"));
		String imageUrl = downloadImage(getParameter("imageUrl"), getParameter("fbUserId"));
		if (!StringUtils.isEmpty(imageUrl))
			user.setImageUrl(imageUrl);
		user.setPassword(encoder.encode(StringUtils.generateRandom(8)));
		user.setRegistrationType(RegistrationType.FACEBOOK);
		return user;
	}

	/**
	 * Download image from facebook server and upload to our server. Return url path
	 * to picture from our server.
	 * 
	 * @param imageUrl
	 * @param fbUserId
	 * @return
	 */
	private String downloadImage(String imageUrl, String fbUserId) {
		if (StringUtils.isEmpty(imageUrl))
			return null;
		try {
			File download = imageDownloader.download(new DefaultIOMetaData(imageUrl, "facebookImg", fbUserId + ".jpg"));
			if (download == null || !download.exists())
				return null;
			Map<String, Object> upload = uploader.upload(download);
			download.delete();
			return String.valueOf(upload.get("secure_url"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
