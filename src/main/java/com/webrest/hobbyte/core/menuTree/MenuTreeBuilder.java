/**
 * 
 */
package com.webrest.hobbyte.core.menuTree;

import java.util.List;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.xml.XMLParser;

/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public class MenuTreeBuilder extends XMLParser<IMenuTreeElement> implements IMenuTreeBuilder {

	private static List<IMenuTreeElement> elements;

	private static final String MENU_TREE_SCHEMA = "config/menu_tree_schema.xml";

	public MenuTreeBuilder() {
		super(MENU_TREE_SCHEMA);
	}

	@Override
	public IMenuTreeElement[] getMenuTreeElements() {
		if (elements != null)
			return elements.toArray(new IMenuTreeElement[elements.size()]);
		elements = process();
		return elements.toArray(new IMenuTreeElement[elements.size()]);
	}

	@Override
	protected void processSingle(Element element) {
		try {
			addToList(new MenuTreeElement(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
