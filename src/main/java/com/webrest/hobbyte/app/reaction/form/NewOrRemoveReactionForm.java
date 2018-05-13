package com.webrest.hobbyte.app.reaction.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONObject;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.reaction.IPostEntryReaction.PostEntryReactions;
import com.webrest.hobbyte.app.reaction.dao.PostEntryReactionDao;
import com.webrest.hobbyte.app.reaction.model.PostEntryReaction;
import com.webrest.hobbyte.app.user.form.dynamic.UserAjaxForm;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class NewOrRemoveReactionForm extends UserAjaxForm {

	private PostEntryDao postEntryDao;

	private PostEntryReactionDao postEntryReactionDao;

	public NewOrRemoveReactionForm(DependencyResolver dependencyResolver) {
		super(dependencyResolver);
		postEntryDao = getDependency(PostEntryDao.class);
		postEntryReactionDao = getDependency(PostEntryReactionDao.class);
	}

	@Override
	protected void process(HttpServletRequest request) throws Exception {
		valid(request);
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
		addJsonValue("action",reactionDBO == null ? "added" : "deleted");
		addJsonValue("postId", String.valueOf(postEntry.getId()));
	}

	@Override
	public Class<?>[] getDependencies() {
		return ArrayUtils.addAll(super.getDependencies(),
				new Class<?>[] { PostEntryDao.class, PostEntryReactionDao.class });
	}

	@Override
	public String getCode() {
		return "new-post-entry";
	}

}
