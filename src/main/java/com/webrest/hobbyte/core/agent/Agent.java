/**
 * 
 */
package com.webrest.hobbyte.core.agent;

import org.springframework.stereotype.Service;

/**
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
@Service
public abstract class Agent {

	public abstract void run() throws Exception;

}
