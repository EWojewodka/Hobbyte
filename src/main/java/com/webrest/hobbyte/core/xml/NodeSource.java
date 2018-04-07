/**
 * 
 */
package com.webrest.hobbyte.core.xml;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.menuTree.MenuTreeElement;
import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * Abstract class for easier convert xml node to java object.
 * 
 * @see MenuTreeElement
 * @see AdminConsole
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public abstract class NodeSource implements FromNodeSource {

	private Element element;

	public NodeSource(Element element) throws Exception {
		Asserts.notNull(element, "Cannot create NodeSource object with null node element.");
		this.element = element;
		init();
	}

	@Override
	public String getAttribute(String attributeName) {
		return element.getAttribute(attributeName);
	}

	@Override
	public String getAttribute(String attributeName, String defaultValue) {
		String attr = getAttribute(attributeName);
		return StringUtils.isEmpty(attr) ? defaultValue : attr;
	}

	protected Element getElement() {
		return element;
	}

}
