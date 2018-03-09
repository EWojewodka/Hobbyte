/**
 * 
 */
package com.webrest.hobbyte.core.db.mediator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
@Component
public class NativeDBMediator implements IDBMediator {

	@Autowired
	private IConnectionManager manager;

	/**
	 * Create instance of {@link NativeDBMediator} with default
	 * {@link IConnectionManager}: {@link ConnectionManager}
	 */
	public NativeDBMediator() {
		this.manager = new ConnectionManager();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.webrest.hobbyte.core.db.mediator.IDBMediator#get(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> getCollection(String query) {
		List<T> list = new ArrayList<>();
		try (Connection con = manager.open();
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(query)) {
			int size = rs.getFetchSize();
			for (int i = 0; i < size; i++)
				list.add((T) rs.getObject(i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.webrest.hobbyte.core.db.mediator.IDBMediator#execute(java.lang.String)
	 */
	@Override
	public boolean execute(String query) {
		try (Connection con = manager.open(); Statement stm = con.createStatement();) {
			return stm.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * Return first result of {@link ResultSet}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getFirst(String query) {
		try (Connection con = manager.open();
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(query)) {
			return (T) rs.getObject(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Set non default connection manager.
	 */
	@Override
	public void setConnectionManager(IConnectionManager manager) {
		this.manager = manager;
	}

}
