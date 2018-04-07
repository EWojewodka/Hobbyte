/**
 * 
 */
package com.webrest.hobbyte.core.console.details;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.console.Console;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public class DetailsConsole extends Console implements IDetailsConsole{
	
	public DetailsConsole(Element element) throws Exception {
		super(element);
	}

	@Override
	public ConsoleType getType() {
		return ConsoleType.DETAILS;
	}

}
