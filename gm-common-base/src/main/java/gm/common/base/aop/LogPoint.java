package gm.common.base.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.util.Date;

@Slf4j
@Aspect
@Component
public class LogPoint {
    @Pointcut(value = " @annotation(gm.common.base.annotation.CostLog)")
    public void addCostPoint(){}

    @Around("addCostPoint()")
    public Object doLog(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = jp.proceed();
        long end = System.currentTimeMillis();
        log.info("======>{} return the request,cost time :{}",jp.getSignature().getName(),(end-start));
        return result;
    }
}
