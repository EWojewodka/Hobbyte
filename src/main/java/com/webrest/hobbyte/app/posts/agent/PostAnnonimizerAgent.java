/**
 * 
 */
package com.webrest.hobbyte.app.posts.agent;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.agent.Agent;

/**
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
@Transactional
@Service("com.webrest.hobbyte.app.posts.agent.PostAnnonimizerAgent")
public class PostAnnonimizerAgent extends Agent {

	@Autowired
	private PostEntryDao postEntryDao;

	@Autowired
	private ExtranetUserDao userDao;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void run() throws Exception {
		List<PostEntry> posts = postEntryDao.findAllBy("author", null);
		ExtranetUser user = userDao.findByEmail("annon@hobbyte.com");
		for (PostEntry post : posts) {
			post.setAuthor(user);
			postEntryDao.save(post);
		}

	}

}
