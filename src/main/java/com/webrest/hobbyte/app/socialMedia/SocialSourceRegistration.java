package com.webrest.hobbyte.app.socialMedia;

import com.webrest.hobbyte.app.user.model.ExtranetUser;

/**
 * @author Emil Wojewódka
 *
 * @since 12 cze 2018
 */
public interface SocialSourceRegistration {

	void validate() throws Exception;

	ExtranetUser buildUser();

}
