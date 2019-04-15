/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.weibo.www.util;


import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *将lambda 中的检查异常转化成runtime异常或替换返回值
 * @author wb-cwb367549
 * @version $Id: Try.java, v 0.1 2019年04月12日 17:18 wb-cwb367549 Exp $
 */
public class Try {

    /**
     * 统一抛出runtime异常
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Function<T, R> ofFunction(uncheckedFunction<T, R> mapper) {
        Objects.requireNonNull(mapper);

        return t -> {
            try {
                return mapper.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * 返回默认值
     * @param mapper
     * @param defaultR
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Function<T, R> ofFunction(uncheckedFunction<T, R> mapper, R defaultR ) {
        Objects.requireNonNull(mapper);
        return t->{
            try {
                return mapper.apply(t);
            } catch (Exception e) {
                return defaultR;
            }
        };
    }

    /**
     * 抛出异常
     * @param consumer
     * @param <T>
     * @return
     */
    public static <T> Consumer<T> ofConsumer(uncheckedConsumer<T> consumer){
        Objects.requireNonNull(consumer);
        return t -> {
            try {
                 consumer.accept(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }


    @FunctionalInterface
    public interface uncheckedFunction<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public static interface uncheckedConsumer<T>{
        void accept(T var1) throws Exception;
    }

    public static void main(String[] args) {
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(1);
        objects.stream().map(Try.ofFunction(s -> throwException(s))).collect(Collectors.toList());
    }

    private static String throwException( int i) throws Exception{
        try {
            throw new  ArrayIndexOutOfBoundsException("111");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new  ArrayIndexOutOfBoundsException("111");
        }
    }

}