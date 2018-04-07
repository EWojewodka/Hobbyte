/**
 * 
 */
package com.webrest.hobbyte.core.console.details;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public interface IDetailsConsole {

	/**
	 * Return parent console.
	 * 
	 * @return
	 */
	IConsole getParent();

	/**
	 * Return {@link Class} of {@link DatabaseObjectImpl} which is handle by this
	 * {@link IDetailsConsole} </br>
	 * <i>Note: if {@link IDetailsConsole} has parent this method'll return parent
	 * {@link IConsole#getBeanClass()}</i>
	 * 
	 * @return
	 */
	Class<? extends DatabaseObjectImpl> getBeanClass();

}
