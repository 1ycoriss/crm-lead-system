package com.crm.lead.aspect;

import com.crm.lead.annotation.OperationLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class OperationLogAspect {

    @AfterReturning("@annotation(operationLog)")
    public void afterReturning(JoinPoint joinPoint, OperationLog operationLog) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String operation = operationLog.value();

        System.out.println("======== 操作日志开始 ========");
        System.out.println("操作名称：" + operation);
        System.out.println("方法名称：" + methodName);
        System.out.println("请求参数：" + Arrays.toString(args));
        System.out.println("操作时间：" + LocalDateTime.now());
        System.out.println("======== 操作日志结束 ========");
    }
}