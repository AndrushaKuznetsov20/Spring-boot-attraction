package com.trueman.attractions.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Прокси класс, который перехватывает запросы для логирования бизнес-логики управления достопримечательностями.
 */
@Aspect
@Component
public class LoggerAttraction {
    private final Logger logger = LoggerFactory.getLogger(LoggerAttraction.class);

    /**
     * Действия, которые будут выполнены до вызова метода.
     */
    @Before("execution(* com.trueman.attractions.services.AttractionService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Вызван метод: " + joinPoint.getSignature().getName());
        logger.info("Аргументы: " + joinPoint.getArgs());
    }

    /**
     * Действия, которые будут выполнены после вызова метода.
     */
    @After("execution(* com.trueman.attractions.services.AttractionService.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Метод завершён : " + joinPoint.getSignature().getName());
    }

    /**
     * Действия, которые будут выполнены в случае выброса исключения одним из методов.
     */
    @AfterThrowing(pointcut = "execution(* com.trueman.attractions.services.AttractionService.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Метод завершился с ошибкой: " + joinPoint.getSignature().getName() + " Исключение: " + exception.getMessage());
    }
}
