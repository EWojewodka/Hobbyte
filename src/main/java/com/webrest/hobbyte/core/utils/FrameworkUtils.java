/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import org.springframework.ui.Model;

/**
 * @author Emil Wojewódka
 *
 * @since 7 kwi 2018
 */
public class FrameworkUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(Model model, String name) {
		return (T) model.asMap().get(name);
	}
	
}
