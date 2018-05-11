package com.webrest.hobbyte.core.console.render;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.IConsole.ConsoleType;
import com.webrest.hobbyte.core.console.list.ListConsole;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.criteria.ICriteriaFilter.OrderDirections;
import com.webrest.hobbyte.core.dao.AbsoluteGenericDao;
import com.webrest.hobbyte.core.model.DatabaseObject;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class DBOConsoleRenderer<T extends DatabaseObject> extends ConsoleRenderer<T> {

	private AbsoluteGenericDao<DatabaseObject> dao;

	@SuppressWarnings("unchecked")
	public DBOConsoleRenderer(DependencyResolver resolver, IConsole console) {
		super(resolver, console);
		this.dao = getDependency(AbsoluteGenericDao.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getObjects() {
		dao.setGenericType((Class<? extends DatabaseObject>) getConsole().getObjectClass());
		return (List<T>) dao.find(getCriteriaFilter());
	}

	protected CriteriaFilter getCriteriaFilter() {
		CriteriaFilter cf = new CriteriaFilter();
		HttpServletRequest request = getContext().getRequest();
		String sortColumn = request.getParameter("sort");
		IConsole console = getConsole();
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

	@Override
	public Class<?>[] getDependencies() {
		return ArrayUtils.addAll(super.getDependencies(), new Class<?>[] { AbsoluteGenericDao.class });
	}

}
