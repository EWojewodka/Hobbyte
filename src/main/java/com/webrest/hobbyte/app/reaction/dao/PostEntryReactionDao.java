package com.webrest.hobbyte.app.reaction.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.reaction.model.PostEntryReaction;

import com.webrest.hobbyte.core.dao.GenericDao;

@Service
public class PostEntryReactionDao extends GenericDao<PostEntryReaction>{

	public List<PostEntryReaction> getPostReactions(int postId) {
		return findAllBy("postEntryId", postId);
	}
	
}
