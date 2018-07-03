/**
 * 
 */
package com.webrest.hobbyte.app.posts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.posts.model.PostEntryFetchRequest;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.criteria.ICriteriaFilter.OrderDirections;
import com.webrest.hobbyte.core.dao.GenericDao;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
@Service
public class PostEntryDao extends GenericDao<PostEntry> {

	public List<PostEntry> getPosts(PostEntryFetchRequest configuration) {
		CriteriaFilter cf = new CriteriaFilter();
		cf.setOrderBy("createdAt");
		cf.setOrderDirection(OrderDirections.DESC);
		cf.setDistinct(true);
		cf.setLimit(configuration.getSize());
		cf.setOffset(configuration.getOffset());
		int userId = configuration.getUserId();
		if (userId > 0)
			cf.addWhere("author.id", userId);
		return find(cf);
	}

	public boolean isReactedByUser(int postId, int userId) {
		CriteriaFilter cf = new CriteriaFilter();
		cf.addWhere("id", postId);
		cf.addWhere("authorId", userId);
		return !find(cf).isEmpty();
	}

}
