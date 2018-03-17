/**
 * 
 */
package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
public class ChangePhoneForm extends UserAjaxForm {

	@Override
	protected JSONObject process(HttpServletRequest request) throws Exception {
		// TODO: implement
		return null;
	}

	@Override
	public String getCode() {
		return "phone";
	}

}
