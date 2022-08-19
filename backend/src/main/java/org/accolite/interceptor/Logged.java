package org.accolite.interceptor;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;;

@Retention(value = RUNTIME)
@Target(value = { METHOD, TYPE })
@InterceptorBinding
public @interface Logged {

}