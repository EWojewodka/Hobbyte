package com.webrest.hobbyte.core.utils;

import com.webrest.hobbyte.core.messages.BasicMessage;
import com.webrest.hobbyte.core.messages.IMessage;
import com.webrest.hobbyte.core.messages.SimpleDecorator;

public class MessageUtils {

	public static String getSimpleMessage(String templateName) {
		IMessage msg = new BasicMessage(templateName);
		try {
			return new SimpleDecorator(msg).decorate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
