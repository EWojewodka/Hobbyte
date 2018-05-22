/**
 * 
 */
package com.webrest.hobbyte.app.posts.http;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webrest.hobbyte.app.posts.PostEntryDao;
import com.webrest.hobbyte.app.posts.form.PostEntryAjax;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.utils.JsonUtils;

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

	@ResponseBody
	@PostMapping(value = "/new")
	public String addPostEntry() {
		return postEntryForm.run();
	}

	@ResponseBody
	@GetMapping(value = "/board")
	public String getPostForBoard() throws JSONException {
		return JsonUtils.toJsonbObject(dao.getRelatedPosts(null)).toString();
	}

}
