/**
 * 
 */
package com.webrest.hobbyte.core.menuTree;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.webrest.hobbyte.core.menuTree.elements.FolderMenuTreeElement;

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
		List<IMenuTreeElement> list = processFolder();
		if (list.isEmpty()) {
			return EMPTY_ARRAY;
		}
		elements = list;
		return elements.toArray(new IMenuTreeElement[elements.size()]);
	}

	/**
	 * @return
	 */
	private List<IMenuTreeElement> processFolder() {
		List<IMenuTreeElement> list = new ArrayList<>();
		Element docEl = doc.getDocumentElement();
		docEl.normalize();
		NodeList nodes = docEl.getChildNodes();
		if (nodes == null || nodes.getLength() == 0)
			return list;
		int len = nodes.getLength();
		for (int i = 0; i < len; i++) {
			Node node = nodes.item(i);
			try {
				list.add(new FolderMenuTreeElement(node));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
		// doc.getDocumentElement().normalize();
		// System.out.println(doc.getDocumentElement().);
		// System.out.println(doc.getDocumentElement().getChildNodes().getLength());
		// NodeList allElements =
		// doc.getElementsByTagName(MenuTreeElementType.FOLDER.getCode());
		// if (allElements == null || allElements.getLength() == 0)
		// return list;
		//
		// int size = allElements.getLength();
		// for (int i = 0; i < size; i++) {
		// try {
		// Node item = allElements.item(i);
		// item.normalize();
		// list.add(new FolderMenuTreeElement(item));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// }
		// return list;
	}

	private Document getDocument() throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document _doc = dBuilder.parse(this.getClass().getClassLoader().getResourceAsStream(MENU_TREE_SCHEMA));
		
		return _doc;
	}

	public static void main(String[] args) {
		MenuTreeBuilder builder = new MenuTreeBuilder();
		builder.getMenuTreeElements();
	}

}
