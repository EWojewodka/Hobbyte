/**
 * 
 */
package com.webrest.hobbyte.core.seed;

import java.util.Date;

/**
 * @author Emil Wojewódka
 *
 * @since 19 maj 2018
 */
public interface ISeed {

	public enum SeedExecuteResult {
		FAILED,
		SKIPED,
		SUCCESS;
	}
	
	boolean needExecute();
	
	SeedExecuteResult execute();
	
	Date getCreateDate();
	
}
