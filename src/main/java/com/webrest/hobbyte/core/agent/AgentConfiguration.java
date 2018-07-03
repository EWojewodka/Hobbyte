/**
 * 
 */
package com.webrest.hobbyte.core.agent;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.seed.SeedConfig;
import com.webrest.hobbyte.core.utils.DateUtils;

/**
 * 
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
@Configuration
@ConditionalOnClass(SeedConfig.class)
public class AgentConfiguration {

	private List<AgentDBO> agents;

	private static final Logger LOGGER = LoggerFactory.getLogger();

	@Autowired
	private AgentDao agentDao;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	private void init() {
		this.agents = agentDao.findAll();
		start();
	}

	private void start() {
		// Find all agent's which should be execute in past, but server was off or smth
		// like this.
		Iterator<AgentDBO> it = agents.parallelStream().filter(
				x -> x.getStatus() != AgentStatus.OFF && x.getNextRun() != null && x.getNextRun().before(new Date()))
				.iterator();

		while (it.hasNext()) {
			AgentDBO agent = it.next();
			startSingleAgent(agent, null);
		}
	}

	public void startSingleAgent(AgentDBO agent, ExtranetUserContext context) {
		// Find agent with bussiness logic by code
		Agent agentService = (Agent) applicationContext.getBean(agent.getAgentClass());

		agent.setStatus(AgentStatus.RUNNING);
		agentDao.save(agent);

		LOGGER.info("Start agent: {} for class {}", agent.getCode(), agentService.getClass());
		try {
			showTraceMessage(context, false,
					String.format("Agent: (%s) is running.", agent.getAgentClass().getSimpleName()));

			// run bussiness logic
			agentService.run();

			showTraceMessage(context, false,
					String.format("Agent: (%s) is finished.", agent.getAgentClass().getSimpleName()));

			int period = agent.getPeriod();

			// If period is 0 it mean's it's not cyclic agent.
			if (period > 0) {
				Date nextRun = new Date(DateUtils.getFutureDate(period));
				agent.setNextRun(nextRun);
			}
			agent.setStatus(AgentStatus.STOPPED);

		} catch (Exception e) {
			showTraceMessage(context, true, String.format("Agent: (%s) throw exception.\n%s",
					agent.getAgentClass().getSimpleName(), e.getMessage()));

			e.printStackTrace();
			agent.setStatus(AgentStatus.INTERUPTED);
			// Let's try again for 5 minutes.
			agent.setNextRun(new Date(DateUtils.getFutureDate(5)));
		}
		LOGGER.info("Finish agent: {} for class: {} with status[{}]", agent.getCode(), agentService.getClass(),
				agent.getStatus());
		// Save agent changes.
		agentDao.save(agent);
	}

	private void showTraceMessage(ExtranetUserContext context, boolean isError, String message) {
		if (context == null)
			return;

		if (isError)
			context.getMessageHandler().addError(message);
		else
			context.getMessageHandler().addSuccess(message);
	}

	public Agent getByCode(String code) {
		return (Agent) applicationContext.getBean(code);
	}

}
