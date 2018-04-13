/**
 * 
 */
package com.webrest.hobbyte.app.posts.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
public class PostEntryAjax extends AjaxDynamicForm {

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
		postEntry.setContent(content);
		PostEntryDao dao = getDependency(PostEntryDao.class);
		dao.save(postEntry);
		JSONObject result = new JSONObject();
		addMessage(result, "Your post is published!");
		return result;
	}

	@Override
	public String getCode() {
		return "new-post-ajax";
	}

	@Override
	public Class<?>[] getDependencies() {
		return new Class[] { PostEntryDao.class };
	}

}
