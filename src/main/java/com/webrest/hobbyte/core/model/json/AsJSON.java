package com.webrest.hobbyte.core.model.json;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AsJSON {

	String defaultValue() default "";

	String defaultDateFormat() default "yyyy-MM-dd HH:mm";
	
	String jsonName() default "";
}
