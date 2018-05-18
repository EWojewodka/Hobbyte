package com.webrest.hobbyte.core.dynamicForm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.utils.Asserts;

@Service
public class AjaxFormFactory {

	private static final List<Class<? extends AjaxDynamicForm>> BUFFER_LIST = new ArrayList<>();

	private AjaxFormFactory() {
	}

	public static void registerForm(Class<? extends AjaxDynamicForm> formClass) {
		Asserts.notNull(formClass, "Cannot register null AjaxDynamicForm");
		BUFFER_LIST.add(formClass);
	}

	/**
	 * If you don't need it, please don't use. Better way is
	 * {@link #getForm(String)} with code, not a {@link Class}
	 * 
	 * @see #getForm(String)
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getForm(Class<? extends AjaxDynamicForm> clazz) {
		Class<? extends AjaxDynamicForm> formClass = BUFFER_LIST.parallelStream().filter(x -> x == clazz).findFirst()
				.orElse(getForm(AjaxDynamicFormNull.class));
		return (T) formClass;
	}

}

@Service
@Scope(WebApplicationContext.SCOPE_APPLICATION)
class AjaxDynamicFormNull extends AjaxDynamicForm {

	private static final Logger LOGGER = LoggerFactory.getLogger();

	@Override
	protected void process(IExtranetUserContext request) throws Exception {
		LOGGER.warn("Invoke " + this.getClass());
	}

	@Override
	public String getCode() {
		return "null";
	}

}
