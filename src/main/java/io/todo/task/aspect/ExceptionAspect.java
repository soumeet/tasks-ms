package io.todo.task.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import static io.todo.task.util.AspectUtil.*;

/**
 * Aspect for all Exception related concerns
 */
@Log4j2
@Aspect
@Service
public class ExceptionAspect extends PointcutExpressions {

    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        LOGGER.trace("Throwing {} from {}", ex.getClass().getName(), getClassMethodName(joinPoint));
    }
}
