/**
 * 
 */
package com.webrest.hobbyte.core.utils.collection;

import java.util.Comparator;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public class PriorityComparer implements Comparator<IPriority> {

	@Override
	public int compare(IPriority o1, IPriority o2) {
		int _o1 = o1.getPriority();
		int _o2 = o2.getPriority();
		if (_o1 > _o2)
			return 1;
		else if (_o2 > _o1)
			return -1;
		return 0;
	}

}
