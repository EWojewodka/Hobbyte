/**
 * 
 */
package com.webrest.hobbyte.core.menuTree;

import java.util.List;


/**
 * Menu tree element. It's object represent of a XML node from
 * menu_tree_schema.xml.
 * 
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public interface IMenuTreeElement {

	/**
	 * Return label name of a element.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Return id of element.
	 * 
	 * @return
	 */
	String getId();

	/**
	 * Return path to resource content.
	 * 
	 * @return
	 */
	String getUri();

	/**
	 * Return element icon.
	 * 
	 * @return
	 */
	String getIcon();

	/**
	 * May return null if it's root element.
	 * 
	 * @return
	 */
	IMenuTreeElement getParent();
	
	List<IMenuTreeElement> getChildren();
}
