/**
 * 
 */
package com.webrest.hobbyte.core.menuTree;

import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithCode;

/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public enum MenuTreeElementType implements WithCode{
	FOLDER("folder"),
	LEAF("leaf");

	private String code;
	
	private MenuTreeElementType(String code) {
		this.code = code;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	public static MenuTreeElementType getByCode(String code) {
		return EnumUtils.findByCode(MenuTreeElementType.class, code);
	}
}
