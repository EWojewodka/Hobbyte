/**
 * 
 */
package com.webrest.hobbyte.core.menuTree.elements;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.webrest.hobbyte.core.menuTree.IMenuTreeElement;
import com.webrest.hobbyte.core.menuTree.MenuTreeElement;
import com.webrest.hobbyte.core.menuTree.MenuTreeElementType;

/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public class FolderMenuTreeElement extends MenuTreeElement {

	private List<IMenuTreeElement> children;

	public FolderMenuTreeElement(Node element) throws Exception {
		super(element);
	}

	@Override
	public IMenuTreeElement initFromNode(Node element) throws Exception {
		this.children = new ArrayList<>();
		super.initFromNode(element);
		NodeList _children = element.getChildNodes();
		
		int len = _children.getLength();
		if (len == 0)
			return this;
		for (int i = 0; i < len; i++) {
			Node child = _children.item(i);
			children.add(new LeafMenuTreeElement(this, child));
		}
		return this;
	}

	@Override
	public MenuTreeElementType getType() {
		return MenuTreeElementType.FOLDER;
	}

	@Override
	public String toString() {
		return super.toString() + "\nFolderMenuTreeElement [children=" + children + "]";
	}
	
	

}
