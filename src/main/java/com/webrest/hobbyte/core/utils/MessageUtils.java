package com.webrest.hobbyte.core.utils;

import com.webrest.hobbyte.core.messages.BasicMessage;
import com.webrest.hobbyte.core.messages.IMessage;
import com.webrest.hobbyte.core.messages.SimpleDecorator;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

public class MessageUtils {

	public static String getSimpleMessage(String templateName) {
		IMessage msg = new BasicMessage(templateName);
		return (String) ExceptionStream.printOnFailure().call(() -> {return new SimpleDecorator(msg).decorate();}).getOrDefault("");
	}

}
