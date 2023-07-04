package com.example.javaadv_task_5.util.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ua.ithillel.lms.logger.Logger;
import ua.ithillel.lms.logger.LoggerConfiguration;
import ua.ithillel.lms.logger.LoggerConfigurationLoader;
import ua.ithillel.lms.logger.file.FileLogger;
import ua.ithillel.lms.logger.file.FileLoggerConfiguration;
import ua.ithillel.lms.logger.file.FileLoggerConfigurationLoader;

@Aspect
@Component
public class LoggingControllerClassesAspect {

    private final Logger log;

    LoggingControllerClassesAspect() {
        LoggerConfigurationLoader lcl = new FileLoggerConfigurationLoader();
        LoggerConfiguration lc = lcl.load("./src/main/resources/application.properties");
        log = new FileLogger((FileLoggerConfiguration) lc);

    }

    @Pointcut("execution(public * com.example.javaadv_task_5.web.EmployeeController.*(..))")
    public void callAtMyControllersPublicMethods() {
    }

    @Around("callAtMyControllersPublicMethods()")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Controller: " + methodName + " - start.");
        try {
            Object result = joinPoint.proceed();
            log.info("Controller " + methodName + " end.");
            return result;
        } catch (Throwable ex) {
            log.info("Exception " + ex + " has been thrown in " + methodName);
            throw ex;
        }
    }
}
