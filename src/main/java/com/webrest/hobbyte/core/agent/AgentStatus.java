/**
 * 
 */
package com.webrest.hobbyte.core.agent;

import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithId;

/**
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
public enum AgentStatus implements WithId {

	STOPPED(0),
	/**/
	RUNNING(1),
	/**/
	INTERUPTED(2),
	/**/
	OFF(3);
	
	private int id;

	private AgentStatus(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	public static AgentStatus getStatusById(int id) {
		return EnumUtils.findById(AgentStatus.class, id);
	}

}
