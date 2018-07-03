package com.webrest.hobbyte.core.cache.console;

import java.util.Collection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.webrest.hobbyte.core.cache.CacheManagerFactory;
import com.webrest.hobbyte.core.cache.ICacheManager;
import com.webrest.hobbyte.core.cache.ICacheMap;
import com.webrest.hobbyte.core.console.handler.ConsoleHandler;
import com.webrest.hobbyte.core.console.render.ToolbarButton;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;

@Service("CacheMapConsoleHandler")
@Scope("session")
public class CacheMapConsoleHandler extends ConsoleHandler<ICacheMap<?, ?>> {

	@Override
	public void handle(ExtranetUserContext context, Model model, String action) throws Exception {
		super.handle(context, model, action);
		if (action.equals("resetAll")) {
			CacheManagerFactory.getCacheManager().resetAll();
		}
	}

	@Override
	protected void initButtons() {
		super.initButtons();
		ToolbarButton button = new ToolbarButton("code");
		button.setLabel("Reset all");
		button.setCodeAction("resetAll");
		button.setIcon("fa fa-eraser");
		addButton(button);
	}

	@Override
	public Collection<ICacheMap<?, ?>> getObjects() {
		ICacheManager cacheManager = CacheManagerFactory.getCacheManager();
		return cacheManager.getAll().values();
	}

}
