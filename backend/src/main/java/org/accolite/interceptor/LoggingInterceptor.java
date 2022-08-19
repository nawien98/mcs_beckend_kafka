package org.accolite.interceptor;

import javax.inject.Inject;
import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

@Logged
@Priority(2020)
@Interceptor
public class LoggingInterceptor {

    @Inject
    Logger logger;

    @AroundInvoke
    Object logInvocation(InvocationContext context) throws Exception {
        logger.info(context.getMethod()+" has started execution.");
        Object ret = context.proceed();
        logger.info(context.getMethod().getName()+" finished execution");
        return ret;
    }

}
