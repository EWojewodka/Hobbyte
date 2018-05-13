/**
 * 
 */
package com.webrest.hobbyte.app.posts.http;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.form.PostEntryAjax;
import com.webrest.hobbyte.app.posts.model.PostEntry;
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
		Arrays.asList(relatedPosts).forEach(p -> array.put(p.getAsJSON()));
		return array.toString();
	}
	
}
