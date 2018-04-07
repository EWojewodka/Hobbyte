package com.webrest.hobbyte.core.console.list;

import java.util.List;

import com.webrest.hobbyte.core.console.IConsole;

/**
 * Interface for expanding {@link IConsole} to {@link ListConsole}. Basicly it's
 * console for review lot of data rows.
 * 
 * @author Emil Wojewódka
 *
 * @since 6 kwi 2018
 */
public interface IListConsole extends IConsole {

	String[] getFields();

	List<String> getSort();

	String getDefaultSort();
	
	boolean canAdd();
	
	boolean canRemove();
	
	String[] getActionsCode();

}
