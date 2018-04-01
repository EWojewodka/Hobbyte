/**
 * 
 */
package com.webrest.hobbyte.core.xml;

import org.w3c.dom.Node;

/**
 * Class for create a java object from xml nodes. </br>
 * 
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public interface FromNodeSource<T> {

	/**
	 * Return parsed object from node element.
	 * 
	 * @param element
	 * @return
	 * @throws Exception
	 */
	T initFromNode(Node element) throws Exception;

}
