/**
 * 
 */
package com.webrest.hobbyte.app.posts.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webrest.hobbyte.app.posts.form.PostEntryAjax;
import com.webrest.hobbyte.core.http.controllers.BaseController;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
@Controller
@RequestMapping(value = "/post")
public class PostEntryController extends BaseController{
	
	@ResponseBody
	@PostMapping(value = "/new")
	public String addPostEntry() {
		PostEntryAjax postEntryAjax = new PostEntryAjax(getDependencyResolver());
		return postEntryAjax.run(getContext());
	}
	
}
