package com.webrest.hobbyte.core.console;

import java.util.List;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.xml.XMLParser;

public class ConsoleBuilder extends XMLParser<IAdminConsole> {

	private static final String CONSOLES_SCHEMA = "config/consoles.xml";

	private static List<IAdminConsole> elements;

	public ConsoleBuilder(){
		super(CONSOLES_SCHEMA);
	}

	public IAdminConsole[] getConsoles() {
		if (elements != null)
			return elements.toArray(new IAdminConsole[elements.size()]);
		elements = process();
		return elements.toArray(new IAdminConsole[elements.size()]);
	}

	@Override
	protected void processSingle(Element element) {
		try {
			addToList(new AdminConsole().initFromNode(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
