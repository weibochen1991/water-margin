package com.weibo.www.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: weibo
 * @Date: 2019/3/24 15:30
 * @Version 1.0
 */
@Data
public class Dog  implements Serializable {

    private static final long serialVersionUID = 7047849019614997683L;
    private String name;
}
