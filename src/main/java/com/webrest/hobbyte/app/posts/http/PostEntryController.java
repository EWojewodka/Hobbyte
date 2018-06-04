/**
 * 
 */
package com.webrest.hobbyte.app.posts.http;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;
import com.webrest.hobbyte.app.posts.PostEntryConst;
import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.PostEntryOptions;
import com.webrest.hobbyte.app.posts.form.PostEntryAjax;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.model.json.View;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
@Controller
@RequestMapping(value = "/post")
public class PostEntryController extends BaseController {

	@Autowired
	private PostEntryDao dao;

	@Autowired
	private PostEntryAjax postEntryForm;

	@JsonView(View.Basic.class)
	@ResponseBody
	@PostMapping(value = "/new")
	public PostEntry addPostEntry() {
		return postEntryForm.run();
	}

	@JsonView(View.Basic.class)
	@ResponseBody
	@GetMapping(value = "/feed/news")
	public List<PostEntry> getPostForBoard(@RequestParam(name = "size", defaultValue = "10") int size)
			throws JSONException {
		List<Integer> showedPosts = getContext().getObject(PostEntryConst.CURRENT_SHOWED_POSTS_KEY);
		
		List<PostEntry> posts = dao.getRelatedPosts(null, size, showedPosts);

		List<Integer> collect = posts.parallelStream().map(x -> x.getId()).collect(Collectors.toList());
		if (showedPosts == null)
			showedPosts = new ArrayList<>();
		showedPosts.addAll(collect);
		getContext().put(PostEntryConst.CURRENT_SHOWED_POSTS_KEY, showedPosts);
		return posts;
	}

	/**
	 * Return available options for specifi post entry.
	 * 
	 * @see PostEntryOptions
	 * @param postId
	 * @return
	 * @throws JSONException
	 */
	@ResponseBody
	@GetMapping(value = "/options")
	public String getPostOptions(@RequestParam(name = "postId", defaultValue = "0") int postId) throws JSONException {
		JSONObject json = new JSONObject();
		if (!getContext().isUserLogged()) {
			json.put("msg", "You must be logged for get post options.");
			return json.toString();
		}

		PostEntry post = dao.findBy("id", postId);
		if (post == null) {
			json.put("msg", String.format("Cannot find post with ID (%s)", postId));
			return json.toString();
		}

		int levelOfOptions = (post.getAuthor().getId() == getContext().getUser().getId() ? 2 : 1);
		List<PostEntryOptions> options = PostEntryOptions.getByOwnerStatus(levelOfOptions);
		JSONArray array = new JSONArray();
		for (PostEntryOptions option : options) {
			array.put(option.getCode());
		}
		json.put("options", array);
		return json.toString();
	}

}
