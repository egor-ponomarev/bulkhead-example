package com.ponomarev.example.bulkhead.aspect;

import com.ponomarev.example.bulkhead.config.BulkheadName;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect which applies bulkhead logic for each rest controller in package v1
 * @author Egor Ponomarev
 */
@Aspect
@Component
public record BulkheadRestControllerAspect(BulkheadRegistry bulkheadRegistry) {

    @Around("within(com.ponomarev.example.bulkhead.rest.v1..*)")
    public Object proceedInternal(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return process(proceedingJoinPoint, BulkheadName.V1.name());
    }

    private Object process(ProceedingJoinPoint proceedingJoinPoint, String bulkheadName) throws Throwable {
        Bulkhead bulkhead = bulkheadRegistry.bulkhead(bulkheadName);
        return bulkhead.executeCheckedSupplier(proceedingJoinPoint::proceed);
    }
}
