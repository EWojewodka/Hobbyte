/**
 * 
 */
package com.webrest.hobbyte.core.console;

import java.lang.reflect.Constructor;
import java.util.List;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.adminPanel.service.ConsoleFinder;
import com.webrest.hobbyte.core.console.handler.ConsoleHandler;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;
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

	private Class<?> objectClass;

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
	public Class<?> getObjectClass() {
		if (objectClass == null)
			objectClass = getParent().getObjectClass();
		return objectClass;
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
	public ConsoleHandler initHandler(DependencyResolver dependencyResolver) throws Exception {
		Constructor<? extends ConsoleHandler> console = consoleHandler.getConstructor(DependencyResolver.class,
				IConsole.class);
		return console.newInstance(dependencyResolver, this);
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	@Override
	public void init() throws Exception {
		fillAttributeMap();
		this.id = getAttribute("id");
		this.objectClass = initObjectClass(getAttribute("bean"));
		this.view = getAttribute("view", "sys/templates/console");
		this.consoleHandler = initConsoleHandler(getAttribute("handler"));
		this.type = ConsoleType.getByCode(getAttribute("type"));
		this.parentConsoleId = getAttribute("parent-id");
		this.name = getAttribute("name");
		this.shortName = initShortName(getAttribute("short-name"));
	}

	private String initShortName(String shortName) {
		return StringUtils.isEmpty(shortName) ? name : shortName;
	}

	/**
	 * Any of object could be show in console.
	 * 
	 * @param objectClassAttribute
	 * @return
	 * @throws Exception
	 */
	protected Class<?> initObjectClass(String objectClassAttribute) throws Exception {
		if (StringUtils.isEmpty(objectClassAttribute))
			return null;
		return Class.forName(objectClassAttribute);
	}

	protected Class<? extends ConsoleHandler> initConsoleHandler(String handlerClass) {
		return ExceptionStream.printOnFailure().call(() -> {
			return StringUtils.isEmpty(handlerClass) ? ConsoleHandler.class : Class.forName(handlerClass);
		}).getOrDefault(ConsoleHandler.class);
	}

}
