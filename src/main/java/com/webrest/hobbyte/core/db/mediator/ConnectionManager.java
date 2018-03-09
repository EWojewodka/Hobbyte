/**
 * 
 */
package com.webrest.hobbyte.core.db.mediator;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

/**
 * Basic database accessor. Can open and close connection. </br>
 * Create instance of {@link DataSource}
 * 
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
@Component
public class ConnectionManager implements IConnectionManager {

	private String propertiesName;

	private DataSource dataSource;

	private Connection connection;

	/**
	 * Close {@link Connection} if is open.
	 * 
	 * @see com.webrest.hobbyte.core.db.mediator.IConnectionManager#close()
	 */
	@Override
	public void close() throws SQLException {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Return open connection
	 * 
	 * @see com.webrest.hobbyte.core.db.mediator.IConnectionManager#open()
	 */
	@Override
	public Connection open() throws SQLException {
		initDataSource();
		if (connection == null || connection.isClosed())
			connection = dataSource.getConnection();
		return connection;
	}

	/**
	 * Create instance of {@link DataSource} - implementation:
	 * {@link DatabaseSource}
	 */
	protected void initDataSource() {
		if (dataSource == null)
			dataSource = new DatabaseSource(propertiesName);
	}

	/**
	 * Set database accesss data properties. </br>
	 * By default {@link ConnectionManager} get it from
	 * <code>application.properties</code>
	 * 
	 * @param propertiesName
	 */
	public void setAccessProperties(String propertiesName) {
		this.propertiesName = propertiesName;
	}
}
