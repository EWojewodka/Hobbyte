package com.webrest.hobbyte.core.console.render;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.ui.Model;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.dao.AbsoluteGenericDao;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.spring.DependencyRequired;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class ConsoleRenderer<T> extends DependencyRequired {

	private IConsole console;

	private ExtranetUserContext context;
	
	private static final List<?> EMPTY_LIST = new ArrayList<>();

	public ConsoleRenderer(DependencyResolver resolver, IConsole console) {
		super(resolver);
		this.console = console;
		this.context = getDependency(ExtranetUserContext.class);
	}
	
	@Override
	public Class<?>[] getDependencies() {
		return new Class<?>[] {AbsoluteGenericDao.class, ExtranetUserContext.class};
	}

	public IConsole getConsole() {
		return console;
	}
	
	public ExtranetUserContext getContext() {
		return context;
	}

	public void render(Model model) {
		model.addAttribute("beans", getObjects());
	}

	@SuppressWarnings("unchecked")
	public Collection<T> getObjects() {
		return (Collection<T>) EMPTY_LIST;
	}

}
