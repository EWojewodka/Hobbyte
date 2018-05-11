package com.webrest.hobbyte.core.utils.functions;

import java.util.function.Function;

public interface SimpleTry {
	
	void action(Function<?,?> onException);

}
