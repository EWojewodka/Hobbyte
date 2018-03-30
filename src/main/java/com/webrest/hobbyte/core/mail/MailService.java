/**
 * 
 */
package com.webrest.hobbyte.core.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Emil Wojewódka
 *
 * @since 18 mar 2018
 */
@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Send mail by {@link JavaMailSender} service.
	 * @see Mail
	 * @param mail
	 */
	public void sendMail(Mail mail) {
		mailSender.send(mail);
	}

}
