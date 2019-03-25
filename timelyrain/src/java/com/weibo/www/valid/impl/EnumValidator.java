package com.weibo.www.valid.impl;

import com.weibo.www.valid.Validator;
import com.weibo.www.valid.exception.IllegalRequestException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: weibo
 * @Date: 2019/3/11 21:06
 * @Version 1.0
 */
public class EnumValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(EnumValidator.class);

    private final String propertyName;

    private final Class enumClass;

    private List<String> propertyValueList;

    public EnumValidator(String propertyName, Class enumClass, String code) {
        this.propertyName = propertyName;
        this.enumClass = enumClass;
        if (StringUtils.isNotBlank(code)) {
            List<String> list = new ArrayList<String>(1);
            list.add(code);
            this.propertyValueList = list;
        }
    }

    public EnumValidator(String propertyName, Class enumClass, List<String> propertyValueList) {
        this.propertyName = propertyName;
        this.enumClass = enumClass;
        this.propertyValueList = propertyValueList;
    }

    public boolean valid() throws IllegalRequestException {
        if(CollectionUtils.isNotEmpty(propertyValueList)){
            try {
                Method method = enumClass.getMethod("contains", new Class[] {String.class});
                for (String code : propertyValueList) {
                    boolean valid = (Boolean) method.invoke(null, new Object[] {code});
                    if (!valid) {
                        throw new IllegalRequestException(propertyName + " : " + code + " is invalid ");
                    }
                }
            } catch (NoSuchMethodException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }

        return false;
    }
}
