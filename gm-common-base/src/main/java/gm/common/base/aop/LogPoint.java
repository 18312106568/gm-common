package gm.common.base.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class LogPoint {
    @Pointcut(value = " @annotation(gm.common.base.annotation.CostLog)")
    public void addCostPoint(){}

    @Around("addCostPoint()")
    public Object doCost(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = jp.proceed();
        long end = System.currentTimeMillis();
        log.info("======>{} return the request,cost time :{}",jp.getSignature().getName(),(end-start));
        return result;
    }


    @Pointcut(value = " @annotation(gm.common.base.annotation.ParamterLog)")
    public void addParamterPoint(){}

    @Before("addParamterPoint()")
    public void doParamter(JoinPoint jp){
        Object[] args = jp.getArgs();
        Object target = jp.getTarget();
        log.info("======>target:{},method:{},params:{}"
                ,target,jp.getSignature().getName(),Arrays.toString(args));
    }
}
