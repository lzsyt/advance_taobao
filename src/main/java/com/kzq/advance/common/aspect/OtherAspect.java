package com.kzq.advance.common.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OtherAspect {
    protected Logger logger = LogManager.getLogger(getClass());

    @Pointcut("execution(* com.kzq.advance..*.*(..))")
    public void poincut() {
    }

    /**
     * 打印类method的名称以及参数
     *
     * @param point 切面
     */
    public void printMethodParams(JoinPoint point) {
        if (point == null) {
            return;
        }
        /**
         * Signature 包含了方法名、申明类型以及地址等信息
         */
        String class_name = point.getTarget().getClass().getName();
        String method_name = point.getSignature().getName();
        String method_args = getMethodArgs(point.getArgs()).toString();

        //重新定义日志
        logger.info("class_name = {}...method_name = {}...method_args = {}", class_name, method_name, method_args);
    }

    private StringBuffer getMethodArgs(Object[] method_args) {
        StringBuffer stringBuffer = new StringBuffer("[");
        if (method_args == null) {
            stringBuffer.append("]");
            return stringBuffer;
        }

        for (int i = 0; i <method_args.length ; i++) {
           if (method_args[i]!=null)
               stringBuffer.append(method_args[i].toString() + ",");
           else
               continue;
        }

        stringBuffer.append("]");
        return stringBuffer;
    }

    @Before("execution(public * com.kzq.advance..*.*(..))")
    public void before(JoinPoint point) {
        this.printMethodParams(point);
    }

}
