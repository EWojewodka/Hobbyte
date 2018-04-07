/**
 * 
 */
package com.webrest.hobbyte.core.menuTree;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.xml.NodeSource;

/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public class MenuTreeElement extends NodeSource implements IMenuTreeElement {

	private String name;

	private String uri;

	private String icon;

	private IMenuTreeElement parent;

	private String id;

	private List<IMenuTreeElement> children;

	public MenuTreeElement(IMenuTreeElement parent, Element node) throws Exception {
		this(node);
		this.parent = parent;
	}

	public MenuTreeElement(Element node) throws Exception {
		super(node);
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
	public void init() throws Exception {
		this.children = new ArrayList<>();

		NamedNodeMap attrs = getElement().getAttributes();
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
			case "console-id":
				if (uri != null)
					throw new IllegalArgumentException("You can't set uri and console-id in menu tree leaf!");
				setUri(String.format("/sys/console?console-id=%s", attrValue));
				break;
			}
		}

		if (getElement().hasChildNodes()) {
			NodeList children = getElement().getChildNodes();
			len = children.getLength();
			for (int i = 0; i < len; i++) {
				Node chilNode = children.item(i);
				if (chilNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				this.children.add(new MenuTreeElement(this, (Element) chilNode));
			}
		}

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
