/**
 * 
 */
package com.webrest.hobbyte.core.adminPanel.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webrest.hobbyte.core.adminPanel.service.ConsoleFinder;
import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.handler.ConsoleHandler;
import com.webrest.hobbyte.core.exception.response.NotFoundException;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.menuTree.IMenuTreeElement;
import com.webrest.hobbyte.core.menuTree.MenuTreeBuilder;
import com.webrest.hobbyte.core.utils.FrameworkUtils;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * It's controller for handle console request and managment this.
 * 
 * @see ConsoleFinder
 * @see IConsole
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
@Controller
@RequestMapping(value = "/sys")
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdminPanelController extends BaseController {

	@Autowired
	private DependencyResolver dependencyResolver;

	@RequestMapping
	public String getMenuTree(Model model) {
		IMenuTreeElement[] mtElements = new MenuTreeBuilder().getMenuTreeElements();
		model.addAttribute("menuTree", mtElements);
		return "sys/index";
	}

	@RequestMapping(value = "/console")
	public String getConsole(Model model) throws Exception {
		IConsole console = ConsoleFinder.getByContext(getContext());
		if (console == null)
			throw new NotFoundException(getContext());

		ConsoleHandler handler = null;
		if (model.containsAttribute("handler")) {
			handler = FrameworkUtils.getAttribute(model, "handler");
			// check if current handler is for this console.
			// It's neccessery because if it's same - we want to still keep this object.
			if (!handler.getConsole().getId().equals(console.getId()))
				handler = console.initHandler(dependencyResolver);
		} else {
			handler = console.initHandler(dependencyResolver);
		}
		model.addAttribute("handler", handler);

		String action = getContext().getRequest().getParameter("action");
		if (!StringUtils.isEmpty(action)) {
			handler = FrameworkUtils.getAttribute(model, "handler");
			handler.handle(getContext(), model, action);
		}
		handler.getRenderer().render(model);

		model.addAttribute("console", console);
		return console.getView();
	}

}
