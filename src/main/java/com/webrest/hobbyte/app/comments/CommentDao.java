/**
 * 
 */
package com.webrest.hobbyte.app.comments;

import java.util.List;

import org.springframework.stereotype.Service;

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

	public List<Comment> getPostComments(int postId) {
		CriteriaFilter cf = new CriteriaFilter("postEntryId", postId);
		cf.setOrderBy("createdAt");
		cf.setOrderDirection(OrderDirections.DESC);
		return find(cf);
	}

}
