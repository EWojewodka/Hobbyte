/**
 * 
 */
package com.webrest.hobbyte.core.console.handler;

import org.springframework.ui.Model;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.render.ConsoleRenderer;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.spring.DependencyRequired;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public class ConsoleHandler extends DependencyRequired implements ViewHandler {

	private IConsole console;

	private ConsoleRenderer renderer;

	public ConsoleHandler(DependencyResolver resolver, IConsole console) {
		super(resolver);
		Asserts.notNull(console, "Cannot init ConsoleHandler for nullable console");
		this.console = console;
		this.renderer = new ConsoleRenderer(resolver, console);
	}

	protected void setRenderer(ConsoleRenderer renderer) {
		this.renderer = renderer;
	}

	public ConsoleRenderer getRenderer() {
		return renderer;
	}

	@Override
	public void handle(ExtranetUserContext context, Model model, String action) throws Exception {
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
		}
	}

	public IConsole getConsole() {
		return console;
	}

	public void onAdd(ExtranetUserContext context, Model model) throws Exception {

	}

	public void onRemove(ExtranetUserContext context, Model model) throws Exception {

	}

	public void onSave(ExtranetUserContext context, Model model) throws Exception {

	}
	
	public void onClone(ExtranetUserContext context, Model model) throws Exception {

	}

}
