package com.webrest.hobbyte.app.reaction.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webrest.hobbyte.app.reaction.form.NewOrRemoveReactionForm;
import com.webrest.hobbyte.core.http.controllers.BaseController;

@Controller
@RequestMapping(value = "/post/reaction")
public class PostEntryReactionController extends BaseController {

	@Autowired
	private NewOrRemoveReactionForm form;
	
	@ResponseBody
	@PostMapping(value = "/add")
	public String postNewReaction() {
		return form.run();
	}

}
