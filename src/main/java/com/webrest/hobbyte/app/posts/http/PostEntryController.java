/**
 * 
 */
package com.webrest.hobbyte.app.posts.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.form.PostEntryAjax;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.controllers.BaseController;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
@Controller
@RequestMapping(value = "/post")
public class PostEntryController extends BaseController{
	
	@Autowired
	private PostEntryDao dao;
	
	@Autowired
	private ExtranetUserDao userDao;
	
	@ResponseBody
	@PostMapping(value = "/new")
	public String addPostEntry() {
		PostEntryAjax postEntryAjax = new PostEntryAjax(getDependencyResolver());
		return postEntryAjax.run(getContext());
	}

	@ResponseBody
	@GetMapping(value ="/board")
	public String getPostForBoard() throws JSONException {
		JSONArray array = new JSONArray();
		PostEntry[] relatedPosts = dao.getRelatedPosts(null);
		for(PostEntry p : relatedPosts) {
			JSONObject json = p.getAsJSON();
			ExtranetUser author = userDao.getById(p.getAuthorId());
			json.put("author", author.getAsJSON());
			array.put(json);
		}
		return array.toString();
	}
	
}
