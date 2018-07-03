/**
 * 
 */
package com.webrest.hobbyte.app.user.http;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.app.user.model.request.WhoToFolloweFetchRequest;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.model.json.View;

/**
 * @author Emil Wojewódka
 *
 * @since 1 lip 2018
 */
@Controller
@RequestMapping(value = "/match/to-follow")
public class ExtranetUserMatcherController extends BaseController {

	@Autowired
	private ExtranetUserDao userDao;

	@JsonView(View.Basic.class)
	@ResponseBody
	@GetMapping
	public List<ExtranetUser> getUsersToFollow(WhoToFolloweFetchRequest fetchRequest) {
		if (!getContext().isUserLogged())
			return Collections.emptyList();
		// TODO:
		return userDao.find(new CriteriaFilter().setLimit(fetchRequest.getSize()));
	}

}
