/**
 * 
 */
package com.webrest.hobbyte.app.posts.agent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.core.agent.Agent;

/**
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
@Service
public class PostAnnonimizerAgent extends Agent {

	@Autowired
	private PostEntryDao postEntryDao;

	@Override
	public void run() throws Exception {
		List<PostEntry> posts = postEntryDao.findAllBy("author", "null");
		System.out.println(posts.size());
	}

}
