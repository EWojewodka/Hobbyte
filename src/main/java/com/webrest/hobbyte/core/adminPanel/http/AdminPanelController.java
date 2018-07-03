/**
 * 
 */
package com.webrest.hobbyte.core.adminPanel.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import com.webrest.hobbyte.core.utils.StringUtils;

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
public class AdminPanelController extends BaseController {

	@Autowired
	private ApplicationContext appContext;

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

		ConsoleHandler<?> handler = (ConsoleHandler<?>) appContext.getBean(console.getHandlerCode());
		handler.setConsole(console);
		getContext().getSession().setAttribute("handler", handler);

		String action = getContext().getRequest().getParameter("action");
		if (!StringUtils.isEmpty(action)) {
			handler.handle(getContext(), model, action);
		}
		handler.render(model);

		model.addAttribute("console", console);
		model.addAttribute("handler", handler);

		return console.getView();
	}

}
