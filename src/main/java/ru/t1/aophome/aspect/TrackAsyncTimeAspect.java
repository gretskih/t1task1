package ru.t1.aophome.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.t1.aophome.model.Track;
import ru.t1.aophome.service.TrackService;

import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class TrackAsyncTimeAspect {
    private final TrackService service;

    @Pointcut("execution(@ru.t1.aophome.annotation.TrackAsyncTime public * *(..))")
    public void trackAsyncTimePointcut() {
    }

    @Around("trackAsyncTimePointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = 0;
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object result;
        try {
            log.info("Выполнение асинхронного метода {} с аргументами {}", methodName, proceedingJoinPoint.getArgs());
            startTime = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            log.error("Ошибка при выполнении метода {}", methodName, e);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;
            CompletableFuture.runAsync(() -> {
                service.save(new Track(null, methodName, runTime));
            });
        }
        return result;
    }
}
