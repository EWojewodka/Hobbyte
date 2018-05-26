/**
 * 
 */
package com.webrest.hobbyte.app.comments.forms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.app.comments.Comment;
import com.webrest.hobbyte.app.comments.CommentDao;
import com.webrest.hobbyte.app.user.form.dynamic.UserAjaxForm;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.i18n.MessageSourceHelper;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 22 maj 2018
 */
@Service
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NewCommentAjax extends UserAjaxForm {

	@Autowired
	private MessageSourceHelper msgHelper;

	@Autowired
	private CommentDao commentDao;

	@Override
	protected void process(IExtranetUserContext context) throws Exception {
		valid();
		String content = getParameter("content");

		AjaxAsserts.notEmpty(content, msgHelper.getMessage("comment.notEmpty", context));

		int entryId = StringUtils.getAsInt(getParameter("postId"), -1);
		AjaxAsserts.greaterThan(entryId, 0, msgHelper.getMessage("internal.error", context));
		ExtranetUser user = getUser();
		Comment comment = new Comment(content, user, entryId);
		commentDao.save(comment);

		addJsonValue("commentId", comment.toString());
	}

	@Override
	public String getCode() {
		return "comment-new-form";
	}

}
