/**
 * 
 */
package com.webrest.hobbyte.core.menuTree;

import java.util.Collection;
import java.util.List;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.cache.CacheMap;
import com.webrest.hobbyte.core.utils.functions.FunctionStream;
import com.webrest.hobbyte.core.utils.functions.TryStream;
import com.webrest.hobbyte.core.xml.XMLParser;

/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public class MenuTreeBuilder extends XMLParser<IMenuTreeElement> implements IMenuTreeBuilder {

	private static final CacheMap<String, IMenuTreeElement> CACHE = new CacheMap<>("menu_tree_elements",
			"Cache for menu tree elements");

	private static final String MENU_TREE_SCHEMA = "config/menu_tree_schema.xml";

	public MenuTreeBuilder() {
		super(MENU_TREE_SCHEMA);
	}

	@Override
	public IMenuTreeElement[] getMenuTreeElements() {
		Collection<IMenuTreeElement> elements = CACHE.getMap().values();
		if (!elements.isEmpty())
			return elements.toArray(new IMenuTreeElement[elements.size()]);
		List<IMenuTreeElement> _elements = process();
		_elements.forEach(el -> CACHE.put(el.getName(), el));
		return _elements.toArray(new IMenuTreeElement[_elements.size()]);
	}

	@Override
	protected void processSingle(Element element) {
		FunctionStream.prepare(e -> {
			e.printStackTrace();
			return null;
		}).call(() -> addToList(new MenuTreeElement(element)));
		
		try (AutoCloseable ac = () -> addToList(new MenuTreeElement(element))) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
