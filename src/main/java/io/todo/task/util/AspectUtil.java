package io.todo.task.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class AspectUtil {

    /**
     * Method to return the formatted string for logging
     *
     * @param joinPoint - JoinPoint
     * @return The formatted string
     */
    public static String getClassMethodName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return String.format("%s # %s", joinPoint.getTarget().getClass().getName(), signature.getName());
    }
}
