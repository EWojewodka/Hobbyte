/**
 * 
 */
package com.webrest.hobbyte.core.creator;

import com.webrest.hobbyte.core.mail.MailBodyCreator;

/**
 * @author Emil Wojewódka
 *
 * @since 19 mar 2018
 */
public interface ICreator<T> {

	/**
	 * Create object. For now - 19.03.2018yr. it's created for
	 * {@link MailBodyCreator}. But in future it would be implement for more types,
	 * perhaps it should be used in some factory.
	 * 
	 * @return
	 * @throws Exception
	 */
	T create() throws Exception;
	
}
