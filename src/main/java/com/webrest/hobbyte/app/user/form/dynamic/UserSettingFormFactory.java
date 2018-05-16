/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.dynamicForm.AjaxDynamicForm;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * TODO: It's not well designed. Forms should be loaded without parameters.
 * 
 * @author Emil Wojewódka
 *
 * @since 15 mar 2018
 */
@Service
public class UserSettingFormFactory {
	
	@Autowired
	private DependencyResolver resolver;

	private static final Map<String, AjaxDynamicForm> FORM_BUFFER = new HashMap<>();

	@PostConstruct
	protected void init() {
		registerForm(new ChangeEmailForm(resolver));
		registerForm(new ChangePhoneForm(resolver));
		registerForm(new ChangeNameForm(resolver));
		registerForm(new ChangeLastnameForm(resolver));

	}

	public static void registerForm(AjaxDynamicForm form) {
		FORM_BUFFER.put(form.getCode(), form);
	}

	public AjaxDynamicForm get(String code) {
		AjaxDynamicForm result = FORM_BUFFER.get(code);
		if (result == null)
			throw new IllegalArgumentException("Cannot find (" + code + ") user form. It's not registered.");
		return result;
	}

}
