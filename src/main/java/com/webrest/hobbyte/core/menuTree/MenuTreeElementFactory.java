/**
 * 
 */
package com.webrest.hobbyte.core.menuTree;

import org.w3c.dom.Node;

import com.webrest.hobbyte.core.menuTree.elements.FolderMenuTreeElement;
import com.webrest.hobbyte.core.utils.Asserts;

/**
 * @author Emil Wojewódka
 *
 * @since 1 kwi 2018
 */
public class MenuTreeElementFactory {

	public IMenuTreeElement create(Node node) throws Exception{
		Asserts.notNull(node, "Cannot create menu tree from null node.");
		MenuTreeElementType type = MenuTreeElementType.getByCode(node.getNodeName());
		switch(type) {
		case FOLDER:
			return new FolderMenuTreeElement(node);
		case LEAF:
			break;
		default:
			break;
		}
		throw new IllegalArgumentException(":<");
	}
	
}
