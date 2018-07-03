/**
 * 
 */
package com.webrest.hobbyte.core.console;

import java.util.List;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.adminPanel.service.ConsoleFinder;
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

	private Class<?> objectClass;

	private String view;

	// private Class<? extends ConsoleHandler> consoleHandler;

	private String consoleHandlerCode;

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
	public ConsoleType getType() {
		return type;
	}

	@Override
	public String getHandlerCode() {
		return consoleHandlerCode;
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
		this.consoleHandlerCode = getAttribute("handler");
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

}
