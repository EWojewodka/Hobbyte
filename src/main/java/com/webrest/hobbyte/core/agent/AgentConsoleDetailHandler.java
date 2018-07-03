/**
 * 
 */
package com.webrest.hobbyte.core.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.webrest.hobbyte.core.console.handler.DBODetailConsolHandler;
import com.webrest.hobbyte.core.console.render.ToolbarButton;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;

/**
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
@Service("AgentConsoleDetailHandler")
@Scope("session")
public class AgentConsoleDetailHandler extends DBODetailConsolHandler<AgentDBO> {

	@Autowired
	private AgentConfiguration agentConf;

	@Override
	public void handle(ExtranetUserContext context, Model model, String action) throws Exception {
		super.handle(context, model, action);

		if (action.equals("run")) {
			runAgent();
		}
	}

	private void runAgent() {
		AgentDBO agent = (AgentDBO) getObject();
		agentConf.startSingleAgent(agent, getContext());
	}

	@Override
	protected void initButtons() {
		super.initButtons();
		ToolbarButton btn = new ToolbarButton("btn");
		btn.setLabel("run");
		btn.setCodeAction("run");
		addButton(btn);
	}

}