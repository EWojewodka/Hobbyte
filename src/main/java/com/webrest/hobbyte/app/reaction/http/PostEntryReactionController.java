package com.webrest.hobbyte.app.reaction.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webrest.hobbyte.app.reaction.form.NewOrRemoveReactionForm;
import com.webrest.hobbyte.core.http.controllers.BaseController;

@Controller
@RequestMapping(value = "/post/reaction")
public class PostEntryReactionController extends BaseController {

	@ResponseBody
	@PostMapping(value = "/add")
	public String postNewReaction() {
		NewOrRemoveReactionForm form = new NewOrRemoveReactionForm(getDependencyResolver());
		return form.run(getContext());
	}

}
