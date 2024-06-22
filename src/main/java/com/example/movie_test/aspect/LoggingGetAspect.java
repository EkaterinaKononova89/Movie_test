package com.example.movie_test.aspect;

import com.example.movie_test.exception.MovieNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Component
@Aspect
public class LoggingGetAspect {
    private final Logger logger = Logger.getLogger(LoggingAddAspect.class.getName());

    @Around("@annotation(ToLogGet)")
    public Object log(ProceedingJoinPoint joinPoint) {
        Object[] argument = joinPoint.getArgs();
        logger.info("Request to get movie from DB " + Arrays.asList(argument));
        try {
            Object resultOfMethod = joinPoint.proceed();
            logger.info("SUCCESSFUL! Film " + Arrays.asList(argument) + " was found, RESULT: " + resultOfMethod);
            return resultOfMethod;
        } catch (Throwable e) {
            logger.info("ERROR: " + e.getMessage());
            throw new MovieNotFoundException(e.getMessage());
        }
    }
}
