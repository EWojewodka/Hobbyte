package com.webrest.hobbyte.core.console;

import java.util.List;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.utils.functions.ExceptionStream;
import com.webrest.hobbyte.core.xml.XMLParser;

public class ConsoleBuilder extends XMLParser<IConsole> {

	private static final String CONSOLES_SCHEMA = "config/consoles.xml";

	private static List<IConsole> elements;

	public ConsoleBuilder() {
		super(CONSOLES_SCHEMA);
	}

	public IConsole[] getConsoles() {
		if (elements != null)
			return elements.toArray(new IConsole[elements.size()]);
		elements = process();
		return elements.toArray(new IConsole[elements.size()]);
	}

	@Override
	protected void processSingle(Element element) {
		ExceptionStream.printOnFailure().call(() -> addToList(ConsoleFactory.createFromElement(element)));
	}

}
