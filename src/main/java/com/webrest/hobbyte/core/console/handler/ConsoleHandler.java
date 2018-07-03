package com.webrest.hobbyte.core.console.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.render.ToolbarButton;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;

/**
 * Basic renderer: {@link ConsoleRenderer}
 * 
 * @author EWojewodka
 *
 */
@Service("ConsoleHandler")
@Scope("session")
public class ConsoleHandler<T> implements ViewHandler {


	@Autowired
	private ExtranetUserContext context;

	private IConsole console;

	private List<ToolbarButton> toolbarButtons = new ArrayList<>();

	@PostConstruct
	protected void init() {
		initButtons();
	}

	@Override
	public void handle(ExtranetUserContext context, Model model, String action) throws Exception {
		if (action.equals("back")) {
			String referer = context.getRequest().getHeader("referer");
			System.out.println(referer);
		}
	}

	public IConsole getConsole() {
		return console;
	}

	public void setConsole(IConsole console) {
		this.console = console;
	}

	public ExtranetUserContext getContext() {
		return context;
	}

	// ---------- RENDER SECTION -------------

	public void render(Model model) {
		model.addAttribute("beans", getObjects());
	}

	@SuppressWarnings("unchecked")
	public Collection<T> getObjects() {
		return Collections.EMPTY_LIST;
	}

	protected void initButtons() {
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
