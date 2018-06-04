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

	private List<ToolbarButton> toolbarButtons = new ArrayList<>();

	public ConsoleRenderer(DependencyResolver resolver, IConsole console) {
		super(resolver);
		this.console = console;
		this.context = getDependency(ExtranetUserContext.class);
		initDefaultButtons();
	}
	
	private void initDefaultButtons() {
		ToolbarButton back = new ToolbarButton("back");
		back.setCodeAction("back");
		back.setIcon("fa fa-arrow-circle-left");
		back.setLabel("back");
		addButton(back);
		
		ToolbarButton refresh = new ToolbarButton("refresh");
		refresh.setCodeAction("refresh");
		refresh.setIcon("fa fa-refresh");
		refresh.setLabel("refresh");
		addButton(refresh);
	}

	@Override
	public Class<?>[] getDependencies() {
		return new Class<?>[] { AbsoluteGenericDao.class, ExtranetUserContext.class };
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

	public List<ToolbarButton> getToolbarButtons() {
		return toolbarButtons;
	}

	public ToolbarButton getToolbarButton(String code) {
		return toolbarButtons.parallelStream().filter(x -> x.getCode().equals(code)).findFirst().get();
	}

	public void addButton(ToolbarButton button) {
		if (toolbarButtons.parallelStream().filter(x -> x.getCode().equals(button.getCode())).count() > 0)
			return;
		toolbarButtons.add(button);
	}

	public void removeButton(String code) {
		toolbarButtons.remove(toolbarButtons.parallelStream().filter(x -> x.getCode().equals(code)).findFirst().get());
	}

}
