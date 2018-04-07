/**
 * 
 */
package com.webrest.hobbyte.core.console;

import java.util.List;

import com.webrest.hobbyte.core.console.handler.ConsoleHandler;
import com.webrest.hobbyte.core.console.handler.Handler;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithCode;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public interface IConsole {

	public static enum ConsoleType implements WithCode {
		CONSOLE("console"), LIST("list"), DETAILS("details");
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

	Class<? extends DatabaseObjectImpl> getBeanClass();

	String getView();

	Class<? extends ConsoleHandler> getConsoleHandler();

	ConsoleType getType();

	IConsole getParent();
	
	List<IConsole> getChildren();

	/**
	 * Create instance of {@link ConsoleHandler} by
	 * {@link ConsoleHandler#ConsoleHandler(IConsole)} constructor where
	 * {@link IConsole} parameter is <b><i>this</i></b> instance of console. </br>
	 * It should be thread-safe, if new request needs a dedicated handler it creates
	 * new from cached console.
	 * 
	 * @see ConsoleHandler
	 * @see Handler
	 * @return
	 * @throws Exception
	 */
	ConsoleHandler initHandler() throws Exception;
	
	String getName();
	
	String getShortName();

}
