/**
 * 
 */
package com.webrest.hobbyte.app.base;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webrest.hobbyte.core.exception.RedirectException;
import com.webrest.hobbyte.core.http.controllers.BaseController;

/**
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
@Controller
public class IndexController extends BaseController{
	
	@RequestMapping(value = "/")
	public String index(Model model) throws RedirectException {
		return "index";
	}
	
}
