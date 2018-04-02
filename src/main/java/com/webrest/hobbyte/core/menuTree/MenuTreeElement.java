/**
 * 
 */
package com.webrest.hobbyte.core.menuTree;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.webrest.hobbyte.core.utils.Asserts;

/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public class MenuTreeElement implements IMenuTreeElement {

	private String name;

	private String uri;

	private String icon;

	private IMenuTreeElement parent;

	private String id;

	private List<IMenuTreeElement> children;

	public MenuTreeElement(IMenuTreeElement parent, Node node) throws Exception {
		this(node);
		this.parent = parent;
	}

	public MenuTreeElement(Node node) throws Exception {
		initFromNode(node);
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setUri(String uri) {
		this.uri = uri;
	}

	protected void setIcon(String icon) {
		this.icon = icon;
	}

	protected void setParent(IMenuTreeElement parent) {
		this.parent = parent;
	}

	protected void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public IMenuTreeElement getParent() {
		return parent;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<IMenuTreeElement> getChildren() {
		return children;
	}

	@Override
	public IMenuTreeElement initFromNode(Node element) throws Exception {
		this.children = new ArrayList<>();

		NamedNodeMap attrs = element.getAttributes();
		Asserts.notEmpty(attrs, "Node attributes cannot be empty.");
		int len = attrs.getLength();
		for (int i = 0; i < len; i++) {

			Node attr = attrs.item(i);
			String attrName = attr.getNodeName();
			String attrValue = attr.getNodeValue();
			switch (attrName) {
			case "name":
				setName(attrValue);
				break;
			case "uri":
				setUri(attrValue);
				break;
			case "id":
				Asserts.notEmpty(attrValue, getError("id"));
				setId(attrValue);
				break;
			case "icon":
				setIcon(attrValue);
				break;
			}
		}

		if (element.hasChildNodes()) {
			NodeList children = element.getChildNodes();
			len = children.getLength();
			for (int i = 0; i < len; i++) {
				Node chilNode = children.item(i);
				if (chilNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				this.children.add(new MenuTreeElement(this, chilNode));
			}
		}

		return this;
	}

	private String getError(String fieldName) {
		return "Menu tree element must has " + fieldName + ".";
	}

	@Override
	public String toString() {
		return "MenuTreeElement [name=" + name + ", uri=" + uri + ", icon=" + icon + ", parent=" + parent + ", id=" + id
				+ ", children=" + (children == null ? 0 : children.size()) + "]";
	}
	

}
