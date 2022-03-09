package com.ony.excetion;

import com.ony.pojo.HttpCode;
import lombok.Getter;

/**
 * 自定义异常类
 * @author Tony
 * @date 2021/8/18
 */
public class CustomException extends BaseException{

    private static final long serialVersionUID = 1L;

    @Getter
    private final HttpCode httpCode;

    public CustomException(String message) {
        super(message);
        this.httpCode = HttpCode.FAILURE;
    }

    public CustomException(HttpCode httpCode) {
        this.httpCode = httpCode;
    }

    public CustomException(String message, HttpCode httpCode) {
        super(message);
        this.httpCode = httpCode;
    }
}
