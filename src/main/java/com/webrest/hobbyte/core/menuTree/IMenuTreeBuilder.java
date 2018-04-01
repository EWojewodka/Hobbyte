/**
 * 
 */
package com.webrest.hobbyte.core.menuTree;

/**
 * Menu tree builder interface for sys/admin panel.
 * 
 * @see #getMenuTreeElements()
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public interface IMenuTreeBuilder {

	/**
	 * Return all of menu tree elements from menu_tree_schema.xml
	 * 
	 * @see IMenuTreeElement
	 * @return
	 */
	IMenuTreeElement[] getMenuTreeElements();

}
