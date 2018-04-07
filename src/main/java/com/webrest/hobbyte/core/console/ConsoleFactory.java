/**
 * 
 */
package com.webrest.hobbyte.core.console;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.console.IConsole.ConsoleType;
import com.webrest.hobbyte.core.console.details.DetailsConsole;
import com.webrest.hobbyte.core.console.list.ListConsole;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public enum ConsoleFactory {
	INSTANCE;

	public static ConsoleFactory getInstance() {
		return ConsoleFactory.INSTANCE;
	}

	public static IConsole createFromElement(Element element) throws Exception {
		if (element == null)
			return null;
		ConsoleType type = ConsoleType.getByCode(element.getAttribute("type"));

		switch (type) {
		case CONSOLE:
			return new Console(element);
		case LIST:
			return new ListConsole(element);
		case DETAILS:
			return new DetailsConsole(element);
		}
		
		return null;
	}

}
