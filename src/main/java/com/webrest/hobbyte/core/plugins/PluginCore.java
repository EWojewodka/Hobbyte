package com.webrest.hobbyte.core.plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.plugins.enums.PluginInvokeAppState;
import com.webrest.hobbyte.core.plugins.interfaces.Command;
import com.webrest.hobbyte.core.plugins.interfaces.IPlugin;
import com.webrest.hobbyte.core.utils.FileUtils;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

/**
 * Abstract implementation of plugin core.
 * 
 * @author Emil
 *
 */
public abstract class PluginCore implements IPlugin {

	private static final Logger LOGGER = LoggerFactory.getLogger();

	private final Package pluginPackage;

	private final File root;

	private List<Command> commands = new ArrayList<>();

	protected PluginCore(Class<? extends PluginCore> clazz) {
		this.pluginPackage = clazz.getPackage();
		this.root = FileUtils.getFile(pluginPackage.getName().replace(".", File.separator));
	}

	@Override
	public void start() {
		for (Command cmd : commands) {
			ExceptionStream.handle(e -> {
				LOGGER.info("Cannot run command ({}) for plugin {}", cmd.getClass().getName(),
						this.getClass().getName());
			}).call(() -> cmd.run());
		}
	}

	@Override
	public void stop() {
		// To override
	}

	/**
	 * Add {@link Command} for run with plugin.
	 * 
	 * @param command
	 */
	public void addCommand(Command command) {
		commands.add(command);
	}

	@Override
	public PluginInvokeAppState getNature() {
		return PluginInvokeAppState.MODULE;
	}

	public Package getPluginPackage() {
		return pluginPackage;
	}

	/**
	 * @return root file of plugin - plugin package.
	 */
	public File getPluginRoot() {
		return root;
	}

}
