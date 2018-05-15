/**
 * 
 */
package com.webrest.hobbyte.core.mail;

import java.util.HashMap;
import java.util.Map;

import com.webrest.hobbyte.core.creator.ICreator;
import com.webrest.hobbyte.core.file.FileService;
import com.webrest.hobbyte.core.utils.FileUtils;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

/**
 * @author Emil Wojewódka
 *
 * @since 19 mar 2018
 */
public class MailBodyCreator {

	private MailBodyCreatorImpl creator;

	public MailBodyCreator(String mailTemplate, Map<String, ?> params) {
		creator = new MailBodyCreatorImpl(mailTemplate, params);
	}

	public String create() {
		return (String) ExceptionStream.printOnFailure().call(() -> {return creator.create();}).getOrDefault("");
	}

	public String getInfo(String name) {
		return creator.getMetaInfo(name);
	}

	/**
	 * I have to hide implementation because a {@link MailBodyCreatorImpl} extends
	 * {@link FileService} which has public methods as a {@link FileService#read()}
	 * and Client should has not permission to access this.
	 * 
	 * @author Emil Wojewódka
	 *
	 * @since 22 mar 2018
	 */
	private class MailBodyCreatorImpl extends FileService implements ICreator<String> {
		private Map<String, ?> params;

		private Map<String, String> metaInfos;

		public MailBodyCreatorImpl(String mailTemplate, Map<String, ?> params) {
			super(FileUtils.getMailTemplate(mailTemplate));
			this.params = params;
		}

		/** {@inheritDoc}} */
		@Override
		public String create() throws Exception {
			String content = read();
			if (metaInfos != null)
				return content;

			metaInfos = new HashMap<>();
			content = StringUtils.replaceVariable(content, params);
			// Get metainfo, as a title
			if (content.startsWith(">")) {
				do {
					int endLine = content.indexOf('\n');
					String metaInfoLine = content.substring(1, endLine);
					int indexOfSplitter = metaInfoLine.indexOf(":");
					if (indexOfSplitter == -1)
						continue;

					String metaInfoName = metaInfoLine.substring(0, indexOfSplitter);
					String metaInfoValue = metaInfoLine.substring(indexOfSplitter + 1);
					content = content.substring(endLine).trim();
					metaInfos.put(metaInfoName, metaInfoValue);
				} while (content.startsWith(">"));

			}
			return content;
		}

		/**
		 * Return mail template metadata as a subject or included css
		 * 
		 * @param name
		 * @return
		 */
		public String getMetaInfo(String name) {
			boolean isThrow = false;
			if (metaInfos == null) {
				isThrow = ExceptionStream.printOnFailure().call(() -> create()).isThrow();
			}
			return isThrow ? null : metaInfos.get(name);
		}
	}
}
