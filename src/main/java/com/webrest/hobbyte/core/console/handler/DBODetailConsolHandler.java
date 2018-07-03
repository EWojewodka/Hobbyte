/**
 * 
 */
package com.webrest.hobbyte.core.console.handler;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.webrest.hobbyte.core.dao.AbsoluteGenericDao;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.model.DatabaseObject;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.utils.ClassUtils;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 15 cze 2018
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service("DBODetailConsolHandler")
@Scope("session")
public class DBODetailConsolHandler<T extends DatabaseObject> extends DBOConsoleHandler<T> {

	@Override
	public void onRemove(ExtranetUserContext context, Model model) throws Exception {
		super.onRemove(context, model);
		DatabaseObject obj = getObject();
		AbsoluteGenericDao dao = getDao();
		dao.setGenericType(obj.getClass());
		dao.delete(obj);
	}

	@Override
	public void onClone(ExtranetUserContext context, Model model) throws Exception {
		super.onClone(context, model);
		DatabaseObjectImpl obj = (DatabaseObjectImpl) getObject();
		AbsoluteGenericDao dao = getDao();
		dao.setGenericType(obj.getClass());
		DatabaseObjectImpl clonned = obj.cloneDBO();
		dao.save(clonned);
	}

	@Override
	public void onSave(ExtranetUserContext context, Model model) throws Exception {
		super.onSave(context, model);
		DatabaseObjectImpl obj = (DatabaseObjectImpl) getObject();
		Class<? extends DatabaseObjectImpl> clazz = obj.getClass();
		HttpServletRequest req = context.getRequest();
		Map<String, String[]> params = req.getParameterMap();
		params.forEach((k, v) -> {
			try {
				Field field = clazz.getDeclaredField(k);
				ClassUtils.setProperty(field, obj, v[0]);
			} catch (NoSuchFieldException | SecurityException e) {
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		AbsoluteGenericDao dao = getDao();
		dao.setGenericType(clazz);
		dao.save(obj);
	}

	public T getObject() {
		AbsoluteGenericDao<?> dao = getDao();
		dao.setGenericType((Class<? extends DatabaseObject>) getConsole().getObjectClass());

		String beanIdString = getContext().getRequest().getParameter("bean-id");
		System.out.println(beanIdString);
		return (T) dao.getById(StringUtils.getAsInt(beanIdString, 0));
	}

	@Override
	public void render(Model model) {
		model.addAttribute("bean", getObject());
	}

}