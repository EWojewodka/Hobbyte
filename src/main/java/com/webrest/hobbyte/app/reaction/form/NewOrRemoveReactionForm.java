package com.webrest.hobbyte.app.reaction.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.reaction.IPostEntryReaction.PostEntryReactions;
import com.webrest.hobbyte.app.reaction.dao.PostEntryReactionDao;
import com.webrest.hobbyte.app.reaction.model.PostEntryReaction;
import com.webrest.hobbyte.app.user.form.dynamic.UserAjaxForm;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.StringUtils;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NewOrRemoveReactionForm extends UserAjaxForm {

	@Autowired
	private PostEntryDao postEntryDao;

	@Autowired
	private PostEntryReactionDao postEntryReactionDao;

	@Override
	protected void process(IExtranetUserContext context) throws Exception {
		valid(context.getRequest());
		PostEntry postEntry = postEntryDao.getById(StringUtils.getAsInt(getParameter("postId"), 0));
		AjaxAsserts.notNull(postEntry, "Cannot add reaction. Internal error.");
		// filter reaction. if there is reaction created by current user - get it else
		// get null.
		PostEntryReaction reactionDBO = postEntry.getReactions().parallelStream()
				.filter(x -> x.getUserId() == getUser().getId()).findFirst().orElse(null);

		if (reactionDBO == null) {
			PostEntryReactions reaction = PostEntryReactions.getByCodeOrElse(getParameter("reaction"),
					PostEntryReactions.LIKE);
			PostEntryReaction reactionModel = new PostEntryReaction(getUser(), postEntry, reaction);
			postEntryReactionDao.save(reactionModel);
		} else {
			postEntryReactionDao.delete(reactionDBO);
		}
		addJsonValue("action", reactionDBO == null ? "added" : "deleted");
		addJsonValue("postId", String.valueOf(postEntry.getId()));
	}

	@Override
	public String getCode() {
		return "new-post-entry";
	}

}
