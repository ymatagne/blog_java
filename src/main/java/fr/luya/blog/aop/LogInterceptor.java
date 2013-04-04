package fr.luya.blog.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;

/**
 * Permet d'intercepter les logs des differentes actions de spring
 * 
 * @author luya
 */
public class LogInterceptor extends CustomizableTraceInterceptor {

    private static final long serialVersionUID = 287162721460370957L;
    protected static Logger logger4J = Logger.getLogger("logger");

    @Override
    protected void writeToLog(Log logger, String message, Throwable ex) {
        if (ex != null) {
            logger4J.debug(message, ex);
        } else {
            logger4J.debug(message);
        }
    }

    @Override
    protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
        return true;
    }
}