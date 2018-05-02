/**
 * 
 */
package com.webrest.hobbyte.app.user.listeners;

import org.springframework.stereotype.Component;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.dao.DaoListenerImpl;

/**
 * @author Emil Wojewódka
 *
 * @since 25 mar 2018
 */
@Component
public class RegistrationListener extends DaoListenerImpl<ExtranetUser> {

//	@Autowired
//	private MailService mailService;
	
	/**
	 * currently registration is not confirm by email.
	 */
	@Override
	public void afterFirstSave(ExtranetUser databaseObject) {
//		super.afterFirstSave(databaseObject);
//		MailBuilder mailBuilder = new Mail.MailBuilder(databaseObject.getEmail(), "registration");
//		HashMap<Object, Object> hashMap = new HashMap<>();
//		hashMap.put("username", databaseObject.getLogin());
//		hashMap.put("confirmation-link", StringUtils.generateRandom(24));
//		try {
//			mailService.sendMail(mailBuilder.build());
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new SilentRuntimeException(e);
//		}
	}

}
