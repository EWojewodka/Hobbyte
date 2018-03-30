package com.webrest.hobbyte.core.messages;

import java.io.File;

import com.webrest.hobbyte.core.file.FileService;
import com.webrest.hobbyte.core.utils.FileUtils;
import com.webrest.hobbyte.core.utils.StringUtils;

public class SimpleDecorator implements IDecorator {

	private IMessage message;

	public SimpleDecorator(IMessage message) {
		this.message = message;
	}

	@Override
	public String decorate() throws Exception {
		File file = FileUtils.getFile(message.getTemplate());
		FileService service = new FileService(file);
		String content = service.read();
		if (StringUtils.isEmpty(content))
			return "";

		return StringUtils.replaceVariable(content, message.getParams());
	}

	public IMessage getMessage() {
		return message;
	}

}