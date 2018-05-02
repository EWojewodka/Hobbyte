/**
 * 
 */
package com.webrest.hobbyte.app.posts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.dao.GenericDao;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
@Service
public class PostEntryDao extends GenericDao<PostEntry>{
	
	public PostEntry[] getRelatedPosts(ExtranetUser user) {
		List<PostEntry> result = find(new CriteriaFilter());
		return result.toArray(new PostEntry[result.size()]);
	}
	
}
