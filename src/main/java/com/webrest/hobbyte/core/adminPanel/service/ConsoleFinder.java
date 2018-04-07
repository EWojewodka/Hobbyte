package com.webrest.hobbyte.core.adminPanel.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import com.webrest.hobbyte.core.console.ConsoleBuilder;
import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.http.context.IHttpContext;
import com.webrest.hobbyte.core.logger.LoggerFactory;

public enum ConsoleFinder {
	INSTANCE;

	private IConsole[] consoles;

	private static final Logger LOGGER = LoggerFactory.getLogger();

	private ConsoleFinder() {
		this.consoles = new ConsoleBuilder().getConsoles();
	}

	private static ConsoleFinder getInstance() {
		return ConsoleFinder.INSTANCE;
	}

	/**
	 * Return {@link IConsole} looking for a "console-id" parameter in request.
	 * 
	 * @param request
	 * @return
	 */
	public static IConsole getByRequest(HttpServletRequest request) {
		String consoleId = request.getParameter("console-id");
		return getById(consoleId);
	}

	public static IConsole getByContext(IHttpContext context) {
		return getByRequest(context.getRequest());
	}

	public static IConsole getById(String consoleId) {
		List<IConsole> result = getByIds(consoleId);
		if (!result.isEmpty())
			return result.get(0);

		LOGGER.info("Cannot find console with id {}", consoleId);
		return null;
	}

	public static List<IConsole> getChildren(IConsole parent) {
		IConsole[] _consoles = getInstance().consoles;
		List<IConsole> result = new ArrayList<>();

		for (IConsole console : _consoles) {
			IConsole _consoleParent = console.getParent();
			if (_consoleParent != null && _consoleParent.getId().equals(parent.getId())) {
				result.add(console);
			}
		}
		return result;
	}

	public static List<IConsole> getByIds(String... consoleIds) {
		if (consoleIds == null || consoleIds.length == 0)
			return new ArrayList<>();
		ConsoleFinder instance = getInstance();
		if (instance == null)
			instance = getInstance();
		IConsole[] _consoles = instance.consoles;
		List<String> listIds = Arrays.asList(consoleIds);
		return Arrays.stream(_consoles).filter(x -> listIds.contains(x.getId())).collect(Collectors.toList());
	}

}
