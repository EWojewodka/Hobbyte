/**
 * 
 */
package com.webrest.hobbyte.core.adminPanel.http;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webrest.hobbyte.core.adminPanel.service.ConsoleFinder;
import com.webrest.hobbyte.core.console.IAdminConsole;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.dao.AbsoluteGenericDao;
import com.webrest.hobbyte.core.exception.response.NotFoundException;
import com.webrest.hobbyte.core.http.controllers.BaseController;
import com.webrest.hobbyte.core.menuTree.IMenuTreeElement;
import com.webrest.hobbyte.core.menuTree.MenuTreeBuilder;
import com.webrest.hobbyte.core.model.DatabaseObject;

/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
@Controller
@RequestMapping(value = "/sys")
public class AdminPanelController extends BaseController{

	@Autowired
	private AbsoluteGenericDao<DatabaseObject> absoluteDao;
	
	@RequestMapping
	public String getAdmin(Model model) {
		IMenuTreeElement[] mtElements = new MenuTreeBuilder().getMenuTreeElements();
		model.addAttribute("menuTree", mtElements);
		return "sys/index";
	}
	
	@GetMapping(value = "/list")
	public String getList(Model model) throws Exception {
		IAdminConsole console = ConsoleFinder.getByContext(getContext());
		if(console == null)
			throw new NotFoundException(getContext());
		absoluteDao.setGenericType(console.getBeanClass());
		model.addAttribute("beans", absoluteDao.find(new CriteriaFilter()));
		model.addAttribute("console", console);
		return "sys/templates/list";
	}
	
}
