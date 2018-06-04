package com.webrest.hobbyte.core.console.handler;

import org.springframework.ui.Model;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.render.ConsoleRenderer;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.spring.DependencyRequired;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * Basic renderer: {@link ConsoleRenderer}
 * 
 * @author EWojewodka
 *
 */
public class ConsoleHandler extends DependencyRequired implements ViewHandler {

	private ConsoleRenderer<?> renderer;

	private IConsole console;
	
	public ConsoleHandler(DependencyResolver resolver, IConsole console) {
		super(resolver);
		Asserts.notNull(console, "Cannot init ConsoleHandler for nullable console");
		this.console = console;
		this.renderer = initRenderer();
	}
	
	@Override
	public Class<?>[] getDependencies() {
		return new Class<?>[] {ExtranetUserContext.class};
	}

	protected ConsoleRenderer<?> initRenderer() {
		return new ConsoleRenderer<>(getDependencyResolver(), getConsole());
	}
	
	@Override
	public void handle(ExtranetUserContext context, Model model, String action) throws Exception {
		if(action.equals("back")) {
			String referer = context.getRequest().getHeader("referer");
			System.out.println(referer);
		}
	}
	
	public ConsoleRenderer<?> getRenderer() {
		return renderer;
	}

	public IConsole getConsole() {
		return console;
	}

}
