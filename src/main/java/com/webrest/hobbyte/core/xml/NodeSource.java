/**
 * 
 */
package com.webrest.hobbyte.core.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.webrest.hobbyte.core.menuTree.MenuTreeElement;
import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.collection.ParameterContainer;

/**
 * Abstract class for easier convert xml node to java object.
 * 
 * @see MenuTreeElement
 * @see AdminConsole
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public abstract class NodeSource extends ParameterContainer implements FromNodeSource {

	private Element element;

	public NodeSource(Element element) throws Exception {
		Asserts.notNull(element, "Cannot create NodeSource object with null node element.");
		this.element = element;
		init();
	}

	@Override
	public String getAttribute(String attributeName) {
		return getOrDefault(attributeName, element.getAttribute(attributeName));
	}

	@Override
	public String getAttribute(String attributeName, String defaultValue) {
		String attr = getAttribute(attributeName);
		return StringUtils.isEmpty(attr) ? defaultValue : attr;
	}

	protected Element getElement() {
		return element;
	}

	/**
	 * Fill {@link ParameterContainer} map by all of xml node attributes. </br>
	 * For example: </br>
	 * 
	 * <b>&lt;node attribute="some-attribute" secondAttribute= "some-other-value"
	 * /&gt;</b> </br>
	 * 
	 * put to map key => attribute value => some-attribute </br>
	 * and </br>
	 * key => secondAttribute value => some-other-value. </br>
	 * Attribute are getting by {@link #getObject(Object)} or
	 * {@link #getOrDefault(Object, Object)}
	 */
	protected void fillAttributeMap() {
		NamedNodeMap attributes = element.getAttributes();
		if (attributes == null)
			return;
		int len = attributes.getLength();
		for (int i = 0; i < len; i++) {
			Node attribute = attributes.item(i);
			put(attribute.getNodeName(), attribute.getNodeValue());
		}
	}

}
