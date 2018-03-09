/**
 * 
 */
package com.webrest.hobbyte.app.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webrest.hobbyte.core.exception.RedirectException;
import com.webrest.hobbyte.core.http.context.IHttpContext;

/**
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
@Controller
public class IndexController {

	@Autowired
	@Qualifier("extranetUser")
	private IHttpContext context;
	
	@RequestMapping(value = "/")
	public String index(Model model) throws RedirectException {
		model.addAttribute("userContext", context);
		return "index";
	}
	
}
