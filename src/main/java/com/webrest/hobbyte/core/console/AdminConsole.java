package com.webrest.hobbyte.core.console;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.xml.FromNodeSource;

public class AdminConsole implements IAdminConsole, FromNodeSource<IAdminConsole> {

	private String uri;

	private String id;

	private Class<? extends DatabaseObjectImpl> beanClass;

	private String[] fields;

	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Class<? extends DatabaseObjectImpl> getBeanClass() {
		return beanClass;
	}

	@Override
	public String[] getFields() {
		return fields;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAdminConsole initFromNode(Element element) throws Exception {
		this.id = element.getAttribute("id");
		this.uri = element.getAttribute("uri");
		this.fields = element.getAttribute("fields").split(",");
		this.beanClass = (Class<? extends DatabaseObjectImpl>) Class.forName(element.getAttribute("bean"));
		return this;
	}

}
