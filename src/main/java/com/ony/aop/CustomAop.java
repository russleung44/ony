package com.ony.aop;

import cn.hutool.json.JSONUtil;
import com.ony.excetion.BaseException;
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
        log.error("==========异常输出==========");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        Object[] args = joinPoint.getArgs();
        StringBuilder params = new StringBuilder();
        for (Object object : args) {
            params.append(JSONUtil.toJsonStr(object)).append(" ");
        }

        Signature signature = joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        log.error("异常请求token:{}", token);
        log.error("异常请求:[ {} {} ]", url, method);
        log.error("异常请求方法:{}", className + "." + methodName);
        log.error("异常请求参数:{}", params);

        if (ex instanceof BaseException) {
            log.error("自定义异常信息:{}", ex.getMessage());
        } else {
            log.error("异常信息", ex);
        }

    }

}