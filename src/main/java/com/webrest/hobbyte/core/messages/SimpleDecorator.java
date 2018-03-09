package com.webrest.hobbyte.core.messages;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.webrest.hobbyte.core.file.FileService;
import com.webrest.hobbyte.core.utils.FileUtils;

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

		return replaceVariables(content, message.getParams());
	}

	protected String replaceVariables(String content, Map<String, Object> params) {
		Iterator<String> it = params.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			content = content.replace("#" + key + "#", params.get(key).toString());
		}
		return content;
	}

}