/**
 * 
 */
package com.webrest.hobbyte.core.console;

import java.lang.reflect.Constructor;
import java.util.List;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.adminPanel.service.ConsoleFinder;
import com.webrest.hobbyte.core.console.handler.ConsoleHandler;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.xml.NodeSource;

/**
 * The simplest type of {@link IConsole}.
 * 
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public class Console extends NodeSource implements IConsole {

	private String id;

	private Class<? extends DatabaseObjectImpl> beanClass;

	private String view;

	private Class<? extends ConsoleHandler> consoleHandler;

	private ConsoleType type;

	private IConsole parentConsole;

	private String parentConsoleId;

	private List<IConsole> children;

	private String name;

	private String shortName;

	public Console(Element element) throws Exception {
		super(element);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Class<? extends DatabaseObjectImpl> getBeanClass() {
		if (beanClass == null)
			beanClass = getParent().getBeanClass();
		return beanClass;
	}

	@Override
	public String getView() {
		return view;
	}

	@Override
	public Class<? extends ConsoleHandler> getConsoleHandler() {
		return consoleHandler;
	}

	@Override
	public ConsoleType getType() {
		return type;
	}

	@Override
	public IConsole getParent() {
		if (parentConsole != null)
			return parentConsole;
		return parentConsole = ConsoleFinder.getById(parentConsoleId);
	}

	@Override
	public List<IConsole> getChildren() {
		if (children != null)
			return children;
		return children = ConsoleFinder.getChildren(this);
	}

	@Override
	public ConsoleHandler initHandler() throws Exception {
		Constructor<? extends ConsoleHandler> console = consoleHandler.getConstructor(IConsole.class);
		return console.newInstance(this);
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws Exception {
		this.id = getAttribute("id");
		this.beanClass = initBeanClass(getAttribute("bean"));
		this.view = getAttribute("view", "sys/templates/console");
		this.consoleHandler = (Class<? extends ConsoleHandler>) Class
				.forName(getAttribute("handler", ConsoleHandler.class.getName()));
		this.type = ConsoleType.getByCode(getAttribute("type"));
		this.parentConsoleId = getAttribute("parent-id");
		this.name = getAttribute("name");
		this.shortName = initShortName(getAttribute("short-name"));
	}

	private String initShortName(String shortName) {
		return StringUtils.isEmpty(shortName) ? name : shortName;
	}

	@SuppressWarnings("unchecked")
	private Class<? extends DatabaseObjectImpl> initBeanClass(String beanClassAttribute) throws Exception {
		if (StringUtils.isEmpty(beanClassAttribute))
			return null;
		return (Class<? extends DatabaseObjectImpl>) Class.forName(beanClassAttribute);
	}

}
