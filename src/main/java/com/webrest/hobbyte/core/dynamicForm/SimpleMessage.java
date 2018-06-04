package com.webrest.hobbyte.core.dynamicForm;

@SuppressWarnings("unused")
public class SimpleMessage {

	public String msg;

	public SimpleMessage(String msg) {
		this.msg = msg;
	}

	public static class ErrorMessage extends SimpleMessage {

		private String error;

		public ErrorMessage(String error) {
			super("");
			this.error = error;
		}

	}

	public static class RedirectMessage extends SimpleMessage {

		public String redirect;

		public RedirectMessage(String path) {
			super("");
			this.redirect = path;
		}

	}

}
