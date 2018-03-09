package com.webrest.hobbyte.core.plugins.interfaces;

import com.webrest.hobbyte.core.plugins.enums.PluginInvokeAppState;

/**
 * Basic interface for plugins.
 * 
 * @author wojew
 *
 */
public interface IPlugin {

	/**
	 * Start plugin.
	 * 
	 */
	void start();
	
	/**
	 * Stop plugin.
	 */
	void stop();
	
	/**
	 * Define plugin's nature.
	 * @return
	 */
	PluginInvokeAppState getNature();
}
