/**
 * 
 */
package com.webrest.hobbyte.core.menuTree.elements;

import org.w3c.dom.Node;

import com.webrest.hobbyte.core.menuTree.IMenuTreeElement;
import com.webrest.hobbyte.core.menuTree.MenuTreeElement;
import com.webrest.hobbyte.core.menuTree.MenuTreeElementType;
import com.webrest.hobbyte.core.utils.Asserts;

/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public class LeafMenuTreeElement extends MenuTreeElement {

	private FolderMenuTreeElement parent;

	public LeafMenuTreeElement(FolderMenuTreeElement parent, Node element) throws Exception {
		super(element);
		Asserts.notNull(parent, "Cannot create leaf menu tree element because parent is null.");
		this.parent = parent;
	}

	@Override
	public IMenuTreeElement initFromNode(Node element) throws Exception {
		super.initFromNode(element);
		setParent(parent);
		return this;
	}

	@Override
	public MenuTreeElementType getType() {
		return MenuTreeElementType.LEAF;
	}

}
