package com.webrest.hobbyte.core.console.render;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.IConsole.ConsoleType;
import com.webrest.hobbyte.core.console.list.ListConsole;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.criteria.ICriteriaFilter.OrderDirections;
import com.webrest.hobbyte.core.dao.AbsoluteGenericDao;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.model.DatabaseObject;
import com.webrest.hobbyte.core.utils.spring.DependencyRequired;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

@SuppressWarnings("unchecked")
public class ConsoleRenderer extends DependencyRequired {

	private IConsole console;

	private AbsoluteGenericDao<DatabaseObject> dao;

	private ExtranetUserContext context;

	public ConsoleRenderer(DependencyResolver resolver, IConsole console) {
		super(resolver);
		this.console = console;
		this.dao = getDependency(AbsoluteGenericDao.class);
		this.context = getDependency(ExtranetUserContext.class);
	}

	public IConsole getConsole() {
		return console;
	}

	public void render(Model model) {
		model.addAttribute("beans", getObjects());
	}

	@Override
	public Class<?>[] getDependencies() {
		return new Class<?>[] { AbsoluteGenericDao.class, ExtranetUserContext.class };
	}

	protected CriteriaFilter getCriteriaFilter() {
		CriteriaFilter cf = new CriteriaFilter();
		HttpServletRequest request = context.getRequest();
		String sortColumn = request.getParameter("sort");
		if (console.getType() == ConsoleType.LIST) {
			ListConsole lConsole = (ListConsole) console;
			Set<Entry<String, String>> es = lConsole.getFieldColumnName().entrySet();
			Iterator<Entry<String, String>> it = es.iterator();
			while (it.hasNext()) {
				Entry<String, String> n = it.next();
				if (n.getValue().equals(sortColumn)) {
					sortColumn = n.getKey();
					break;
				}
			}
		}
		cf.setOrderDirection(
				OrderDirections.getByName(request.getParameter("order")) == OrderDirections.ASC ? OrderDirections.DESC
						: OrderDirections.ASC);
		cf.setOrderBy(sortColumn);
		return cf;
	}

	public List<DatabaseObject> getObjects() {
		dao.setGenericType(console.getBeanClass());
		return dao.find(getCriteriaFilter());
	}

}
