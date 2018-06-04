package com.webrest.hobbyte.app.comments.listeners;

import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.comments.Comment;
import com.webrest.hobbyte.core.dao.DaoListenerImpl;

@Service
public class CommentNotificationListener extends DaoListenerImpl<Comment> {

	@Override
	public void afterFirstSave(Comment comment) {
		super.afterFirstSave(comment);
	}

}
