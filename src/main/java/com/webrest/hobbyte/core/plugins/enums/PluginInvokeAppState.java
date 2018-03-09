package com.webrest.hobbyte.core.plugins.enums;

import com.webrest.hobbyte.core.plugins.interfaces.IPlugin;

/**
 * This enum define when plugin should be started. </br>
 * 
 * @see IPlugin
 * @author Emil
 *
 */
public enum PluginInvokeAppState {

	BEFORE_START(0),
	/**
	 * 
	 */
	AFTER_START(1),
	/**
	 * 
	 */
	NOT_ACTIVE(-1),
	/**
	 * 
	 */
	MODULE(10);
	
	private int id;
	
	private PluginInvokeAppState(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
