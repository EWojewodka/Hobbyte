/**
 * 
 */
package com.webrest.hobbyte.core.db.mediator;

/**
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
public class DBMediatorFactory {

	public static IDBMediator create() {
		return new NativeDBMediator();
	}
	
}
