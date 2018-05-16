package com.webrest.hobbyte.core.dynamicForm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.cache.CacheMap;
import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.utils.Asserts;

@Service
public class AjaxFormFactory {

	private static final CacheMap<String, AjaxDynamicForm> FORM_BUFFER = new CacheMap<>("dynamic forms");

	private AjaxFormFactory() {
	}

	public static void registerForm(AjaxDynamicForm form) {
		Asserts.notNull(form, "Cannot register null AjaxDynamicForm");
		String code = form.getCode();
		Asserts.notEmpty(code, "Cannot register DynamicForm (" + form.getClass() + ") for nullable code!");
		FORM_BUFFER.put(code, form);
	}

	public AjaxDynamicForm getForm(Class<? extends AjaxDynamicForm> clazz) {
		Map<String, AjaxDynamicForm> map = FORM_BUFFER.getMap();
		return map.values().parallelStream().filter(x -> x.getClass() == clazz).findFirst().orElse(getForm("null"));
	}

	public AjaxDynamicForm getForm(String code) {
		return FORM_BUFFER.get(code);
	}

}

@Service
class AjaxDynamicFormNull extends AjaxDynamicForm {

	private static final Logger LOGGER = LoggerFactory.getLogger();

	@Override
	protected void process(HttpServletRequest request) throws Exception {
		LOGGER.warn("Invoke " + this.getClass());
	}

	@Override
	public String getCode() {
		return "null";
	}

}
