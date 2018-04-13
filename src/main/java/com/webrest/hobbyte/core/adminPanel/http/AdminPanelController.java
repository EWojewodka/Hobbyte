/**
 * 
 */
package com.webrest.hobbyte.core.adminPanel.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webrest.hobbyte.core.adminPanel.service.ConsoleFinder;
import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.handler.ConsoleHandler;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.dao.AbsoluteGenericDao;
import com.webrest.hobbyte.core.exception.response.NotFoundException;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.menuTree.IMenuTreeElement;
import com.webrest.hobbyte.core.menuTree.MenuTreeBuilder;
import com.webrest.hobbyte.core.model.DatabaseObject;
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
public class AdminPanelController extends BaseController {

	@Autowired
	private AbsoluteGenericDao<DatabaseObject> absoluteDao;

	@Autowired
	private DependencyResolver dependencyResolver;

	@RequestMapping
	public String getMenuTree(Model model) {
		IMenuTreeElement[] mtElements = new MenuTreeBuilder().getMenuTreeElements();
		model.addAttribute("menuTree", mtElements);
		return "sys/index";
	}

	@GetMapping(value = "/console")
	public String getConsole(Model model) throws Exception {
		IConsole console = ConsoleFinder.getByContext(getContext());
		if (console == null)
			throw new NotFoundException(getContext());

		absoluteDao.setGenericType(console.getBeanClass());
		CriteriaFilter cf = new CriteriaFilter();
		cf.setOrderBy(getContext().getRequest().getParameter("sort"));
		model.addAttribute("beans", absoluteDao.find(cf));

		if (model.containsAttribute("handler")) {
			ConsoleHandler handler = FrameworkUtils.getAttribute(model, "handler");
			// check if current handler is for this console.
			if (!handler.getConsole().getId().equals(console.getId()))
				model.addAttribute("handler", console.initHandler(dependencyResolver));
		} else {
			model.addAttribute("handler", console.initHandler(dependencyResolver));
		}

		if (!StringUtils.isEmpty(getContext().getRequest().getParameter("action"))) {
			ConsoleHandler handler = FrameworkUtils.getAttribute(model, "handler");
			handler.handle(getContext(), model);
		}
		model.addAttribute("console", console);
		return console.getView();
	}

}
