package com.webrest.hobbyte.core.plugins;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.logger.IActionLogger;
import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.plugins.enums.PluginInvokeAppState;
import com.webrest.hobbyte.core.plugins.interfaces.IPlugin;
import com.webrest.hobbyte.core.utils.ClassUtils;

@Service
@Scope("prototype")
public class PluginManager {

	private static final Logger LOGGER = LoggerFactory.getLogger();

	private Map<PluginInvokeAppState, IPlugin[]> plugins;

	// Find and sort project plugins.
	private PluginManager() {
		Set<Class<? extends IPlugin>> _plugins = ClassUtils.onlyNonAbstract(ClassUtils.findSubTypes(IPlugin.class));
		PluginSort sorter = new PluginSort(_plugins);
		sorter.sort();
		plugins = sorter.getPlugins();
	}
	
	/**
	 * Return array of plugins which has specified nature.
	 * 
	 * @param nature
	 * @return
	 */
	public IPlugin[] getByNature(PluginInvokeAppState nature) {
		return plugins.get(nature);
	}

	/**
	 * Run all plugins which specified nature.
	 * 
	 * @param nature
	 */
	public void loadByType(PluginInvokeAppState nature) {
		IPlugin[] _pl = getByNature(nature);
		if (_pl == null || _pl.length == 0) {
			LOGGER.info("There're no {} nature plugins", nature.name());
			return;
		}

		for (IPlugin plugin : _pl) {
			load(plugin);
		}
	}

	/**
	 * Load plugin.
	 * 
	 * @see PluginManager#loadByType(PluginInvokeAppState)
	 * @param plugin
	 */
	public void load(IPlugin plugin) {
		IActionLogger actionLogger = LoggerFactory.getActionLogger("Start plugin " + plugin.getClass().getSimpleName());
		actionLogger.start();
		plugin.start();
		actionLogger.stop();
	}

}