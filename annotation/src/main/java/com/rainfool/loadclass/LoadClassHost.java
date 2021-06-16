package com.rainfool.loadclass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author krystian
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = {ElementType.TYPE})
public @interface LoadClassHost {
}
