package com.webrest.hobbyte.core.cache.console;

import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;

import com.webrest.hobbyte.core.cache.CacheManagerFactory;
import com.webrest.hobbyte.core.cache.ICacheManager;
import com.webrest.hobbyte.core.cache.ICacheMap;
import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.handler.ConsoleHandler;
import com.webrest.hobbyte.core.console.render.ConsoleRenderer;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class CacheMapConsoleHandler extends ConsoleHandler {

	public CacheMapConsoleHandler(DependencyResolver resolver, IConsole console) {
		super(resolver, console);
	}
	
	@Override
	public Class<?>[] getDependencies() {
		return ArrayUtils.addAll(super.getDependencies(), new Class<?>[] {DependencyResolver.class});
	}

	@Override
	protected ConsoleRenderer<?> initRenderer() {
		return new CacheMapConsoleRenderer(getDependency(DependencyResolver.class));
	}

	private class CacheMapConsoleRenderer extends ConsoleRenderer<ICacheMap<?, ?>> {

		public CacheMapConsoleRenderer(DependencyResolver resolver) {
			super(resolver, CacheMapConsoleHandler.this.getConsole());
		}

		@Override
		public Collection<ICacheMap<?, ?>> getObjects() {
			ICacheManager cacheManager = CacheManagerFactory.getCacheManager();
			return cacheManager.getAll().values();
		}

	}

}