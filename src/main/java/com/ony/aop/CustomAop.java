package com.ony.aop;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tony
 * @date 2021/8/18
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CustomAop {


    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void apiController() {
    }

    @AfterThrowing(throwing = "ex", pointcut = "apiController()")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {

        log.error("=========异常输出()=========");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");
        // 请求方式
        String method = request.getMethod();
        // URL
        String url = request.getRequestURL().toString();
        // 请求参数
        Object[] args = joinPoint.getArgs();
        StringBuilder params = new StringBuilder();
        for (Object object : args) {
            params.append(JSONUtil.toJsonStr(object)).append(" ");
        }
        // 切点签名
        Signature signature = joinPoint.getSignature();
        // 类名
        String className = joinPoint.getTarget().getClass().getName();
        // 方法名
        String methodName = signature.getName();

        log.error("异常请求token:{}", token);
        log.error("异常请求:[ {} {} ]", url, method);
        log.error("异常请求方法:{}", className + "." + methodName);
        log.error("异常请求参数:{}", params);
        log.error("异常信息", ex);

    }

}