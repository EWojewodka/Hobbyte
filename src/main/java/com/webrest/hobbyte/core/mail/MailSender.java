/**
 * 
 */
package com.webrest.hobbyte.core.mail;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.file.properties.IEnvironmentProperties;
import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.platform.PlatformInfo;
import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 18 mar 2018
 */
@Service
@Primary
public class MailSender extends JavaMailSenderImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger();

	@Autowired
	private PlatformInfo platformInfo;

	@PostConstruct
	protected void init() {
		IEnvironmentProperties usingProperties = platformInfo.getUsingProperties();
		setHost(usingProperties.getMailHost());
		setPort(StringUtils.getAsInt(usingProperties.getMailPort()));
		setUsername(usingProperties.getMailUsername());
		setPassword(usingProperties.getMailPassword());

		Properties prop = getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.mail.javamail.JavaMailSenderImpl#doSend(javax.mail.
	 * internet.MimeMessage[], java.lang.Object[])
	 */
	@Override
	protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
		try {
			super.doSend(mimeMessages, originalMessages);
		} catch (Exception e) {
			// Log email addresses which are not available.
			SimpleMailMessage[] mails = (SimpleMailMessage[]) originalMessages;
			List<String> failureEmails = Arrays.stream(mails).map(x -> x.getTo()[0]).collect(Collectors.toList());
			LOGGER.info("Cannot send mails to " + StringUtils.toGenericString(failureEmails, ","));
		}
	}

}
