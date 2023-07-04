package com.example.javaadv_task_5.util.annotations;

import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collection;
import ua.ithillel.lms.logger.Logger;
import ua.ithillel.lms.logger.LoggerConfiguration;
import ua.ithillel.lms.logger.LoggerConfigurationLoader;
import ua.ithillel.lms.logger.file.FileLogger;
import ua.ithillel.lms.logger.file.FileLoggerConfiguration;
import ua.ithillel.lms.logger.file.FileLoggerConfigurationLoader;

@Aspect
@Component
public class LoggingServiceClassesAspect {

    private final Logger log;

    LoggingServiceClassesAspect() {
        LoggerConfigurationLoader lcl = new FileLoggerConfigurationLoader();
        LoggerConfiguration lc = lcl.load("./src/main/resources/application.properties");
        log = new FileLogger((FileLoggerConfiguration) lc);
    }

    @Pointcut("execution(public * com.example.javaadv_task_5.service.*Service.*(..))")
    public void callAtMyServicesPublicMethods() {
    }

    @Around(value = "callAtMyServicesPublicMethods() && args(returningValue)")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object returningValue) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug("Service " + methodName + " - start.");
        try {
            Object result = joinPoint.proceed();
            Object outputValue = Optional.ofNullable(result)
                .map(v -> v instanceof byte[] ? "File as byte[] " : v)
                .map(v -> v instanceof Collection<?> ? "Collection size: " +  ((Collection<?>) v).size() : v)
                .orElse(null);
            log.debug("Service " + methodName + " - end.");
            return result;
        } catch (Throwable ex) {
            log.debug("Exception " + ex + " has been thrown in " + methodName);
            throw ex;
        }
    }
}
