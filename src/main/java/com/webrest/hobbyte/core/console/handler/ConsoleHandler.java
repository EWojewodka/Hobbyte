/**
 * 
 */
package com.webrest.hobbyte.core.console.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.ClassUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public class ConsoleHandler implements ViewHandler {

	private IConsole console;

	private Map<Class<?>, Object> dependenciesMap = new HashMap<>();
	
	public ConsoleHandler(IConsole console) {
		Asserts.notNull(console, "Cannot init ConsoleHandler for nullable console");
		this.console = console;
	}

	@Override
	public void handle(ExtranetUserContext context, Model model) throws Exception {
	}

	public IConsole getConsole() {
		return console;
	}

	@Override
	public void addDependency(Class<?> clazz, Object obj) {
		dependenciesMap.put(clazz, obj);
	}
	
	@Override
	public Class<?>[] getDependencies() {
		return ClassUtils.EMPTY_ARRAY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDependency(Class<T> clazz) {
		return (T) dependenciesMap.get(clazz);
	}

}
