/**
 * 
 */
package com.webrest.hobbyte.app.comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;
import com.webrest.hobbyte.app.comments.forms.NewCommentAjax;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.model.json.View;

/**
 * @author Emil Wojewódka
 *
 * @since 21 maj 2018
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController extends BaseController {

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private NewCommentAjax newCommentForm;

	@ResponseBody
	@GetMapping(value = "/get")
	public List<Comment> getComments(@RequestParam(name = "post-id", required = true) int postId,
			@RequestParam(name = "count", defaultValue = "25") int count) {
		return commentDao.getPostComments(postId, count);
	}

	@JsonView(View.Basic.class)
	@ResponseBody
	@PostMapping(value = "/new")
	public Comment addComment() {
		return newCommentForm.run();
	}

}
