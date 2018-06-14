/**
 * 
 */
package com.webrest.hobbyte.core.agent;

import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.dao.GenericDao;

/**
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
@Service
public class AgentDao extends GenericDao<AgentDBO> {

	public AgentDBO getByCode(String code) {
		return findBy("code", code);
	}

}
