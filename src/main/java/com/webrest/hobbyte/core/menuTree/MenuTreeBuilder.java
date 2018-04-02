/**
 * 
 */
package com.webrest.hobbyte.core.menuTree;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public class MenuTreeBuilder implements IMenuTreeBuilder {

	private static List<IMenuTreeElement> elements;

	private static final IMenuTreeElement[] EMPTY_ARRAY = new IMenuTreeElement[0];

	private static final String MENU_TREE_SCHEMA = "config/menu_tree_schema.xml";

	private Document doc;

	public MenuTreeBuilder() {
	}

	@Override
	public IMenuTreeElement[] getMenuTreeElements() {
		if (elements != null)
			return elements.toArray(new IMenuTreeElement[elements.size()]);

		try {
			this.doc = getDocument();
		} catch (Exception e) {
			e.printStackTrace();
			return EMPTY_ARRAY;
		}
		List<IMenuTreeElement> list = processAll();
		if (list.isEmpty()) {
			return EMPTY_ARRAY;
		}
		elements = list;
		return elements.toArray(new IMenuTreeElement[elements.size()]);
	}

	/**
	 * @return
	 */
	private List<IMenuTreeElement> processAll() {
		List<IMenuTreeElement> list = new ArrayList<>();
		NodeList nodes = doc.getDocumentElement().getChildNodes();
		if (nodes == null || nodes.getLength() == 0)
			return list;
		int len = nodes.getLength();
		for (int i = 0; i < len; i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			try {
				list.add(new MenuTreeElement(node));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private Document getDocument() throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document _doc = dBuilder.parse(this.getClass().getClassLoader().getResourceAsStream(MENU_TREE_SCHEMA));
		return _doc;
	}

	public static void main(String[] args) {
		MenuTreeBuilder builder = new MenuTreeBuilder();
		IMenuTreeElement[] mt = builder.getMenuTreeElements();
		for(IMenuTreeElement m : mt) {
			System.out.println(m);
		}
	}

}
