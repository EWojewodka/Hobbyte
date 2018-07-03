/**
 * 
 */
package com.webrest.hobbyte.core.console;

import java.util.List;

import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithCode;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public interface IConsole {

	public static enum ConsoleType implements WithCode {
		CONSOLE("console"), LIST("list"), DETAILS("details"), PARAMS("params");
		private String code;

		private ConsoleType(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}

		public static ConsoleType getByCode(String code) {
			return EnumUtils.findByCode(ConsoleType.class, code);
		}

	}

	String getId();

	Class<?> getObjectClass();

	String getView();
	
	String getHandlerCode();

	ConsoleType getType();

	IConsole getParent();

	List<IConsole> getChildren();

	String getName();

	String getShortName();

}
