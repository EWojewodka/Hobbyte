/**
 * 
 */
package com.webrest.hobbyte.app.comments;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.comments.listeners.CommentNotificationListener;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.criteria.ICriteriaFilter.OrderDirections;
import com.webrest.hobbyte.core.dao.GenericDao;

/**
 * @author Emil Wojewódka
 *
 * @since 21 maj 2018
 */
@Service
public class CommentDao extends GenericDao<Comment> {

	@Autowired
	private CommentNotificationListener commentNotificationListener;

	@PostConstruct
	private void init() {
		addListener(commentNotificationListener);
	}

	public List<Comment> getPostComments(int postId, int size) {
		CriteriaFilter cf = new CriteriaFilter("postEntryId", postId);
		cf.setOrderBy("createdAt");
		cf.setOrderDirection(OrderDirections.DESC);
		cf.setLimit(size);
		return find(cf);
	}

	public List<Comment> getPostComments(int postId) {
		return getPostComments(postId, Integer.MAX_VALUE);
	}

}
