package com.weibo.www.valid;

import com.weibo.www.valid.exception.IllegalRequestException;

/**
 * @Author: weibo
 * @Date: 2019/3/11 20:55
 * @Version 1.0
 */
public interface Validator {
    boolean valid() throws IllegalRequestException;
}
