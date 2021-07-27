package io.todo.task.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static io.todo.task.util.AspectUtil.getClassMethodName;

/**
 * Aspect class for all logging related concerns
 */
@Log4j2
@Aspect
@Component
public class LoggingAspect extends PointcutExpressions {

    /**
     * Method to print the entry log
     *
     * @param joinPoint - JoinPoint variable
     */
    @Before("pointcut()")
    public void logBeforeAllMethods(JoinPoint joinPoint) {
        LOGGER.debug("Entry into : {}", getClassMethodName(joinPoint));
    }

    /**
     * Method to print the exit log
     *
     * @param joinPoint - JoinPoint variable
     */
    @After("pointcut()")
    public void logAfterAllMethods(JoinPoint joinPoint) {
        LOGGER.debug("Exit from : {}", getClassMethodName(joinPoint));
    }
}
