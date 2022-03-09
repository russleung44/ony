package com.ony.excetion;

import lombok.NoArgsConstructor;

/**
 * @author TonyLeung
 * @date 2022/2/8
 */
@NoArgsConstructor
public class BaseException extends RuntimeException{

    public BaseException(String message) {
        super(message);
    }
}
