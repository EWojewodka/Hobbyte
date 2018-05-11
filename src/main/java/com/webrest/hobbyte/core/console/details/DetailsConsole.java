/**
 * 
 */
package com.webrest.hobbyte.core.console.details;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.console.Console;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public class DetailsConsole extends Console {
	
	private boolean canSave;
	
	private boolean canRemove;
	
	private boolean canClone;
	
	public DetailsConsole(Element element) throws Exception {
		super(element);
	}

	@Override
	public ConsoleType getType() {
		return ConsoleType.DETAILS;
	}

	@Override
	public void init() throws Exception {
		super.init();
		this.canSave = StringUtils.getAsBoolean(getAttribute("save"), false);
		this.canRemove = StringUtils.getAsBoolean(getAttribute("remove"), false);
		this.canClone = StringUtils.getAsBoolean(getAttribute("clone"), false);
	}

	public boolean canSave() {
		return canSave;
	}

	public boolean canRemove() {
		return canRemove;
	}

	public boolean canClone() {
		return canClone;
	}

}
