package com.webrest.hobbyte.core.plugins;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.plugins.enums.PluginInvokeAppState;
import com.webrest.hobbyte.core.plugins.interfaces.IPlugin;
import com.webrest.hobbyte.core.utils.ClassUtils;

/**
 * Main class for sorting IPlugins by their nature. </br>
 * After {@link #sort()} there's generated map with sorted plugins.
 * 
 * @author Emil
 *
 */
public class PluginSort {

	private Collection<Class<? extends IPlugin>> plugins;
	
	private Map<PluginInvokeAppState, IPlugin[]> result = new HashMap<>(PluginInvokeAppState.values().length);
	
	private static final Logger LOGGER = LoggerFactory.getLogger();
	
	/**
	 * Init plugin sorter by 
	 * 
	 * @param plugins
	 */
	public PluginSort(Collection<Class<? extends IPlugin>> plugins) {
		this.plugins = plugins;
	}
	
	/**
	 * Sort passes to constructor plugins by their nature. </br>
	 * After sorting use #getPlugins to get sorted plugins.
	 * @see PluginManager
	 */
	public void sort() {
		if(plugins == null || plugins.size() == 0) return;
		Map<PluginInvokeAppState, Collection<IPlugin>> tmpMap = initializeMap();
		
		Iterator<Class<? extends IPlugin>> it = plugins.iterator();
		while(it.hasNext()) {
			Class<? extends IPlugin> next = it.next();
			try {
				//try to create an instance of class.
				Constructor<? extends IPlugin> constructor = next.getConstructor(ClassUtils.EMPTY_ARRAY);
				IPlugin instance = constructor.newInstance((Object[])ClassUtils.EMPTY_ARRAY);
				PluginInvokeAppState pluginNature = instance.getNature();
				//put instance to tmpMap
				if(pluginNature!=null) {
					tmpMap.get(pluginNature).add(instance);
				}
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOGGER.info("Cannot initialize constructor of plugin ({})\n{}", next.getName(), e.getMessage());
				continue;
			}
		}
		
		//convert map with list as values to an array for better efficient.
		Collection<PluginInvokeAppState> natures = tmpMap.keySet();
		for(PluginInvokeAppState n : natures) {
			Collection<IPlugin> values = tmpMap.get(n);
			result.put(n, values.toArray(new IPlugin[values.size()]));
		}
	}

	//Create temporary map with list for contains plugins.
	private Map<PluginInvokeAppState, Collection<IPlugin>> initializeMap() {
		PluginInvokeAppState[] values = PluginInvokeAppState.values();
		Map<PluginInvokeAppState, Collection<IPlugin>> map = new HashMap<>(values.length);
		
		for(PluginInvokeAppState nature : values) {
			List<IPlugin> list = new ArrayList<>();
			map.put(nature, list);
		}
		return map;
	}
	

	/**
	 * Return sorted plugins.
	 * @return
	 */
	public Map<PluginInvokeAppState, IPlugin[]> getPlugins(){
		return result;
	}
}

