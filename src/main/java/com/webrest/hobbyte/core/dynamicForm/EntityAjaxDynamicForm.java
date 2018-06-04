package com.webrest.hobbyte.core.dynamicForm;

import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

public abstract class EntityAjaxDynamicForm<T> extends GenericAjaxDynamicForm<T> {

	@Override
	public T run() {
		return ExceptionStream.handle((e) -> {
			return handleException(e);
		}).call(() -> {
			return process(getContext());
		}).get();
	}

	public abstract T handleException(Exception e);

}

class JsonNullObject {

	private String msg = "";

	public String getMsg() {
		return msg;
	}

}
