/**
 * 
 */
package com.webrest.hobbyte.core.agent;

/**
 * @author Emil Wojewódka
 *
 * @since 22 cze 2018
 */
public class AgentException extends Exception {

	private static final long serialVersionUID = 1051845682244797607L;

	public AgentException(AgentDBO agent) {
		super("Cannot run agent (" + agent + ")");
	}

}
