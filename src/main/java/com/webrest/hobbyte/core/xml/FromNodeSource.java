/**
 * 
 */
package com.webrest.hobbyte.core.xml;

import org.w3c.dom.Element;

/**
 * Class for create a java object from xml nodes. </br>
 * 
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public interface FromNodeSource {

	/**
	 * Return parsed object from node element.
	 * 
	 * @param element
	 * @return
	 * @throws Exception
	 */
	void init() throws Exception;

	/**
	 * Return attribute of {@link Element}
	 * 
	 * @see #getAttribute(String)
	 * @return
	 */
	String getAttribute(String attributeName);

	/**
	 * Return attribute of {@link Element} or if is null or blank return
	 * defaultValue
	 * 
	 * @see #getAttribute() 
	 * @param defaultValue
	 * @return
	 */
	String getAttribute(String attributeName, String defaultValue);

}
