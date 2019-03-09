package br.edu.unisep.fx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyNumber {

	boolean isDecimal() default false;
	
	int decimalCount() default 0;
	
	boolean allowsNegative() default true;
	
	boolean money() default false;
}
