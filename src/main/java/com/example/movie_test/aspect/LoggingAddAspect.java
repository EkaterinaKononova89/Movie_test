package com.example.movie_test.aspect;

import com.example.movie_test.exception.ErrorInputData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAddAspect {
    private final Logger logger = Logger.getLogger(LoggingAddAspect.class.getName());

    @Around("@annotation(ToLogAdd)")
    public Object log(ProceedingJoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        logger.info("Request for adding movie " + Arrays.asList(arguments));
        try {
            Object resultOfMethod = joinPoint.proceed();
            logger.info("SUCCESSFUL! Film " + resultOfMethod + " was added");
            return resultOfMethod;
        } catch (Throwable e) {
            logger.info("ERROR: " + e.getMessage());
            throw new ErrorInputData(e.getMessage());
        }
    }
}
