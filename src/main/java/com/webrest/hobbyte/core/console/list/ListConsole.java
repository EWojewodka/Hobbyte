/**
 * 
 */
package com.webrest.hobbyte.core.console.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.adminPanel.service.ConsoleFinder;
import com.webrest.hobbyte.core.console.Console;
import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public class ListConsole extends Console implements IListConsole {

	private Map<String, String> fieldColumnName;

	private String[] fields;

	private List<String> sort;

	private String defaultSort;

	private boolean canAdd;

	private boolean canRemove;

	private String[] actions;

	public ListConsole(Element element) throws Exception {
		super(element);
	}

	@Override
	public ConsoleType getType() {
		return ConsoleType.LIST;
	}

	@Override
	public String[] getFields() {
		return fields;
	}

	@Override
	public List<String> getSort() {
		return sort;
	}

	@Override
	public String getDefaultSort() {
		return defaultSort;
	}

	public boolean canAdd() {
		return canAdd;
	}

	public boolean canRemove() {
		return canRemove;
	}

	@Override
	public String[] getActionsCode() {
		return actions;
	}

	@Override
	public void init() throws Exception {
		super.init();
		this.fieldColumnName = initFieldMap(getAttribute("fields"));
		this.fields = fieldColumnName.keySet().toArray(new String[fieldColumnName.keySet().size()]);
		this.sort = initSortFields(getAttribute("sort"));
		this.defaultSort = getAttribute("sort-default");
		this.canAdd = StringUtils.getAsBoolean(getAttribute("new"), false);
		this.canRemove = StringUtils.getAsBoolean(getAttribute("remove"), false);
		this.actions = initActions(getAttribute("actions"));
	}

	/**
	 * Return map which mapping column name and name of bean property. For example
	 * if you need a 'createdAt' object property but in {@link Console} table header
	 * this value should be under 'created' define in console xml definition
	 * <i>createdAt#created</i>. </br>
	 * </br>
	 * key -> property name </br>
	 * value -> column name
	 * 
	 * @param fieldsAttribute
	 * @return
	 */
	private Map<String, String> initFieldMap(String fieldsAttribute) {
		String[] fields = fieldsAttribute.split(",");
		Map<String, String> map = new LinkedHashMap<>(fields.length);
		for (String s : fields) {
			int indexOf = s.indexOf('#');
			if (indexOf > -1)
				map.put(s.substring(0, indexOf), s.substring(++indexOf, s.length()));
			else
				map.put(s, s);
		}
		return map;
	}

	private String[] initActions(String actionAttribute) {
		if (StringUtils.isEmpty(actionAttribute))
			return new String[0];
		return actionAttribute.split(",");
	}

	public List<IConsole> getActionsConsole() {
		return ConsoleFinder.getByIds(actions);
	}

	// If attribute "sort".equals("all") let's copy #fields to result list.
	private List<String> initSortFields(String elementAttribute) {
		List<String> result = new ArrayList<>();
		if (StringUtils.isEmpty(elementAttribute))
			return result;

		if (elementAttribute.equals("all"))
			result.addAll(fieldColumnName.values());
		else
			result.addAll(Arrays.asList(elementAttribute.split(",")));
		return result;
	}

	public Map<String, String> getFieldColumnName() {
		return fieldColumnName;
	}

	
}
