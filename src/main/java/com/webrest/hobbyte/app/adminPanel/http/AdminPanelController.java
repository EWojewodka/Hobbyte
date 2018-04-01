/**
 * 
 */
package com.webrest.hobbyte.app.adminPanel.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.menuTree.MenuTreeBuilder;

/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
@Controller
@RequestMapping(value = "/sys")
public class AdminPanelController extends BaseController{


	@RequestMapping
	public String getAdmin() {
		new MenuTreeBuilder().getMenuTreeElements();
		return "sys/index";
	}
	
}
