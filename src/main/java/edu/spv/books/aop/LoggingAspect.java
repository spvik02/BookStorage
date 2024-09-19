package edu.spv.books.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* edu.spv.books.service..*(..))")
    private void serviceLayerMethod() {
    }

    @Before("serviceLayerMethod()")
    public void logsRequest(JoinPoint joinPoint) {
        String logMsg = "Called method: " + getCalledMethodInfo(joinPoint);
        log.debug(logMsg);
    }

    @AfterReturning(
            value = "serviceLayerMethod()",
            returning = "returnValue"
    )
    public void logsResponse(JoinPoint joinPoint, Object returnValue) {

        String logMsg = "Called method: " +
                getCalledMethodInfo(joinPoint) +
                "; Response: " +
                returnValue;

        log.debug(logMsg);
    }

    @AfterThrowing(
            value = "serviceLayerMethod()",
            throwing = "exception"
    )
    public void logsErrors(JoinPoint joinPoint, Throwable exception) {
        String logMsg = "Called method: " +
                getCalledMethodInfo(joinPoint) +
                "; Exception: " +
                exception.getMessage();

        log.error(logMsg);
    }

    /**
     * Returns String with called method information
     *
     * @param joinPoint
     * @return String with called method information
     */
    private String getCalledMethodInfo(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName() +
                " " +
                Arrays.toString(joinPoint.getArgs()) +
                " in " +
                joinPoint.getTarget().getClass().getSimpleName();
    }
}
