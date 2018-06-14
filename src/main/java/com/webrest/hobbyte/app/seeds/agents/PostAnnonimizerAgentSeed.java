/**
 * 
 */
package com.webrest.hobbyte.app.seeds.agents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.posts.agent.PostAnnonimizerAgent;
import com.webrest.hobbyte.core.agent.AgentDBO;
import com.webrest.hobbyte.core.agent.AgentDao;
import com.webrest.hobbyte.core.seed.AbstractSeed;

/**
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
@Service
public class PostAnnonimizerAgentSeed extends AbstractSeed {

	@Autowired
	private AgentDao agentDao;

	public PostAnnonimizerAgentSeed() {
		super("2018-06-14");
	}

	@Override
	public boolean needExecute() {
		return agentDao.findBy("agentClass", PostAnnonimizerAgent.class) == null;
	}

	@Override
	public SeedExecuteResult execute() {
		AgentDBO agent = new AgentDBO();
		agent.setAgentClass(PostAnnonimizerAgent.class);
		agent.setCode(PostAnnonimizerAgent.class.getName());
		agent.setPeriod(1);
		agentDao.save(agent);
		return SeedExecuteResult.SUCCESS;
	}

}
