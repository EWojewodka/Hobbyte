package com.webrest.hobbyte.core.model.json;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface AsJSON {

	String defaultValue() default "";

	String defaultDateFormat() default "yyyy-MM-dd HH:mm";
}