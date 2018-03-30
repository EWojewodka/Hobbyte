/**
 * 
 */
package com.webrest.hobbyte.core.mail;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * Mail object which imitate a real mail object.
 * 
 * @author Emil Wojewódka
 *
 * @since 18 mar 2018
 */
public class Mail extends SimpleMailMessage {

	private static final long serialVersionUID = 2492563136008983310L;

	private Mail(MailBuilder builder) {
		if (StringUtils.isEmpty(builder.body))
			throw new IllegalArgumentException("Cannot send mail without text! We're not a scam mailer! :<");
		setSubject(builder.subject);
		setFrom("Hobbyte");
		setText(builder.body);
		setTo(builder.to);
	}

	@Override
	public String toString() {
		String old = super.toString();
		old = old.replace("SimpleMailMessage", new Date() + " mail");
		return old.replace("; ", "\n");
	}

	public static class MailBuilder {

		private String subject;

		private String to[];

		private String body;

		private Map<String, Object> params;

		/**
		 * File name from templates/mails.
		 */
		private String templateName;

		/**
		 * 
		 * @param to
		 *            - email address
		 * @param templateName
		 *            - simple name to resources/templates/mails/content, e.g
		 *            'registration'
		 */
		public MailBuilder(String to, String templateName) {
			this.to = new String[] { to };
			this.templateName = templateName;
		}

		/**
		 * Set mail subject
		 * 
		 * @param subject
		 * @return
		 */
		public MailBuilder setSubject(String subject) {
			this.subject = subject;
			return this;
		}

		/**
		 * Replace all current params by new.
		 * 
		 * @param params
		 * @return
		 */
		public MailBuilder setParams(Map<String, Object> params) {
			this.params = params;
			return this;
		}

		/**
		 * Add single value to params. If {@link #params} is null, let's create new
		 * object and put value there.
		 * 
		 * @param name
		 * @param value
		 * @return
		 */
		public MailBuilder addParam(String name, Object value) {
			if (params == null)
				params = new HashMap<>();
			params.put(name, value);
			return this;
		}

		/**
		 * Return new {@link Mail} instance with property passed by setters in
		 * {@link MailBuilder} </br>
		 * <i>Note: if e.g. subject is null or empty - system will be search an subject
		 * value in email template. Look at {@link MailBodyCreator#create()}</i>
		 * 
		 * @return
		 * @throws Exception
		 */
		public Mail build() throws Exception {
			MailBodyCreator bodyCreator = new MailBodyCreator(templateName, params);
			body = bodyCreator.create();
			if (StringUtils.isEmpty(subject))
				subject = bodyCreator.getInfo("title");
			return new Mail(this);
		}

	}

}
