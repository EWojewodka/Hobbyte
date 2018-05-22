/**
 * 
 */
package com.webrest.hobbyte.core.seed;

import java.util.Date;

import com.webrest.hobbyte.core.utils.DateUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 19 maj 2018
 */
public abstract class AbstractSeed implements ISeed {

	private Date date;

	public AbstractSeed(String date) {
		this.date = DateUtils.parseDate(date);
	}

	public Date getCreateDate() {
		return date;
	}

}
