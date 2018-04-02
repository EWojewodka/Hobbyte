package com.webrest.hobbyte.core.adminPanel.service;

import javax.servlet.http.HttpServletRequest;

import com.webrest.hobbyte.core.console.ConsoleBuilder;
import com.webrest.hobbyte.core.console.IAdminConsole;
import com.webrest.hobbyte.core.http.context.IHttpContext;
import com.webrest.hobbyte.core.utils.StringUtils;

public enum ConsoleFinder {
	INSTANCE;

	private IAdminConsole[] consoles;

	private ConsoleFinder() {
		this.consoles = new ConsoleBuilder().getConsoles();
	}

	private static ConsoleFinder getInstance() {
		return ConsoleFinder.INSTANCE;
	}

	/**
	 * Return {@link IAdminConsole} looking for a "console-id" parameter in request.
	 * 
	 * @param request
	 * @return
	 */
	public static IAdminConsole getByRequest(HttpServletRequest request) {
		ConsoleFinder instance = getInstance();
		String consoleId = request.getParameter("console-id");
		if (StringUtils.isEmpty(consoleId))
			return null;
		IAdminConsole[] _consoles = instance.consoles;
		int len = _consoles.length;
		for (int i = 0; i < len; i++) {
			IAdminConsole console = _consoles[i];
			if (console.getId().equals(consoleId))
				return console;
		}
		return null;
	}

	public static IAdminConsole getByContext(IHttpContext context) {
		return getByRequest(context.getRequest());
	}

}
