/**
 * 
 */
package com.webrest.hobbyte.core.console.handler;

import org.springframework.ui.Model;

import com.webrest.hobbyte.core.http.context.ExtranetUserContext;

/**
 * @author Emil Wojewódka
 *
 * @since 7 kwi 2018
 */
public interface ViewHandler {

	void handle(ExtranetUserContext context, Model model, String action) throws Exception;

}
