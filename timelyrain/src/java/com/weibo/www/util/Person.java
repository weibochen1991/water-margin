package com.weibo.www.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: weibo
 * @Date: 2019/3/24 15:27
 * @Version 1.0
 */
@Data
public class Person implements Serializable {
    private static final long serialVersionUID = 2536967295368395487L;
    private String name ;

    private Dog dog;

}
