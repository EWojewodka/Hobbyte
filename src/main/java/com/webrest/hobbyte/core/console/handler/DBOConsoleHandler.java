/**
 * 
 */
package com.webrest.hobbyte.core.console.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.IConsole.ConsoleType;
import com.webrest.hobbyte.core.console.list.ListConsole;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.criteria.ICriteriaFilter.OrderDirections;
import com.webrest.hobbyte.core.dao.AbsoluteGenericDao;
import com.webrest.hobbyte.core.dao.GenericDao;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.model.DatabaseObject;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
@Service("DBOConsoleHandler")
@Scope("session")
public class DBOConsoleHandler<T extends DatabaseObject> extends ConsoleHandler<T> {

	@Autowired
	private AbsoluteGenericDao<?> dao;

	@Override
	public void handle(ExtranetUserContext context, Model model, String action) throws Exception {
		super.handle(context, model, action);
		switch (action) {
		case "add":
			onAdd(context, model);
			break;
		case "remove":
			onRemove(context, model);
			break;
		case "save":
			onSave(context, model);
			break;
		case "clone":
			onClone(context, model);
			break;
		}
	}

	public void onAdd(ExtranetUserContext context, Model model) throws Exception {

	}

	public void onRemove(ExtranetUserContext context, Model model) throws Exception {

	}

	public void onSave(ExtranetUserContext context, Model model) throws Exception {

	}

	public void onClone(ExtranetUserContext context, Model model) throws Exception {

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

	@SuppressWarnings("unchecked")
	public <D extends GenericDao<?>> D getDao() {
		return (D) dao;
	}

}
