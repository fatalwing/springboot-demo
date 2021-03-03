package com.townmc.boot.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.StaleStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

/**
 * @author meng
 */
@Aspect
@Component
public class RetryOnOptimisticLockingAspect {
    private static final Logger log = LoggerFactory.getLogger(RetryOnOptimisticLockingAspect.class);
    public static final int maxRetries = 5;//最多重试的次数

    @Pointcut("@annotation(com.townmc.boot.utils.annotations.RetryOnOptimisticLockingFailure)")//自定义的注解作为切点
    public void retryOnOptFailure() {}

    @Around("retryOnOptFailure()")//around注解可以在 目标方法 之前执行 也可以在目标方法之后
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        int numAttempts = 0;
        do {
            numAttempts++;
            try {
                return pjp.proceed();
            } catch (Exception e) {
                if (e instanceof ObjectOptimisticLockingFailureException || e instanceof StaleStateException || e instanceof JpaSystemException) {
                    log.info("---更新数据乐观锁重试中---");
                    if (numAttempts > maxRetries){
                        log.info("抛出异常");
                        throw e;
                    }
                }

            }
        }while (numAttempts < maxRetries);
        return null;
    }

}
