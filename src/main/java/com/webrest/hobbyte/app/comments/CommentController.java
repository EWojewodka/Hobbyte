/**
 * 
 */
package com.webrest.hobbyte.app.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.utils.JsonUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 21 maj 2018
 */
@Controller
@RequestMapping(value = "/comments")
public class CommentController extends BaseController {

	@Autowired
	private CommentDao commentDao;

	@GetMapping
	@ResponseBody
	public String getComments(@RequestParam(name = "post-id", required = true) int postId) {
		return JsonUtils.toJsonObject(commentDao.getPostComments(postId)).toString();
	}

}
