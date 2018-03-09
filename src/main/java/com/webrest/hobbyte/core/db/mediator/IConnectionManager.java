/**
 * 
 */
package com.webrest.hobbyte.core.db.mediator;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
public interface IConnectionManager {

	/**
	 * Close connection with database
	 * @throws SQLException
	 */
	void close() throws SQLException;
	
	/**
	 * Open connection with database.
	 * @return
	 * @throws SQLException
	 */
	Connection open() throws SQLException;
	
}
