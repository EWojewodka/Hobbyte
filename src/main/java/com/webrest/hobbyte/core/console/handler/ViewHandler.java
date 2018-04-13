/**
 * 
 */
package com.webrest.hobbyte.core.console.handler;

import org.springframework.ui.Model;

import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.spring.IDependencyRequired;

/**
 * @author Emil Wojewódka
 *
 * @since 7 kwi 2018
 */
public interface ViewHandler extends IDependencyRequired {

	void handle(ExtranetUserContext context, Model model) throws Exception;

}
