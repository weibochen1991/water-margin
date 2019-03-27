package com.weibo.www.util;


import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.beans.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * clone包括深clone和浅clone,浅clone对象引用和之前对象内存地址相同，一个改变都回改变
 * 而深clone则引用不相同，但不如实现对象中所有引用对象的clone方法，这还是会有部分共用其地址
 * 每个需要clone的类都实现clone方法比较麻烦
 * 该类主要通过buffer 来实现对象的clone，
 * @Author: weibo
 * @Date: 2019/3/24 14:07
 * @Version 1.0
 */
public class BeanUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 使用自省机制 转成bean (json 也可以)
     * @param obj
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String,Object> bean2Map(Object obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Map<String, Object> map = new HashMap<>(propertyDescriptors.length);
        Stream.of(propertyDescriptors).filter(p-> Objects.equal("class",p.getName()))
                .forEach(p->{
                    try {
                        map.put(p.getName(), p.getReadMethod().invoke(obj));
                    } catch (Exception e) {
                        LOGGER.error("bean to map error");
                    }
                });
        return map;
    }

    /**
     * 使用BeanMap转换
     * @param obj
     * @return
     */
    public static Map<String,Object> bean2Map2(Object obj){
        HashMap<String, Object> map = Maps.newHashMap();
        BeanMap beanMap = BeanMap.create(obj);
        for(Object key :beanMap.keySet()){
            map.put(String.valueOf(key), beanMap.get(key));
        }
        return map;
    }




    /**
     * bean 转化
   * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Optional<T> beanConvert(Object source ,Class<T> target){
        T t = null;
        if(source == null){
            return Optional.ofNullable(null);
        }
        try {
          t= target.newInstance();
        } catch (Exception e) {
            LOGGER.error("bean initialize error");
        }
        BeanUtils.copyProperties(source,t);
        return Optional.ofNullable(t);
    }



    /**
     * 使用序列话反序列化实现 使用该方法的类必须实现serializable
     */
    public static Optional<Object> deepClone(Serializable src){
        Object dest = null;
        ObjectInputStream ois = null;
        try(
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
        ){
                oos.writeObject(src);
                byte[] bytes = baos.toByteArray();
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                ois = new ObjectInputStream(bais);
                dest = ois.readObject();
        }catch (Exception e){
        }finally {
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
        }
        return Optional.ofNullable(dest);
    }

    public static void main(String[] args) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Optional<String> s1 = beanConvert(null, String.class);
        s1.ifPresent(s -> System.out.println(s1));

        Person person = new Person();
        person.setName("111");
        Dog dog = new Dog();
        dog.setName("wawa");
        person.setDog(dog);
        System.out.println(person);
        System.out.println(person.getDog());
        System.out.println(System.identityHashCode(person));
        System.out.println(System.identityHashCode(person.getDog()));
        System.out.println("==========");
        Optional<Object> clone = deepClone(person);
        clone.map(s->(Person)s).ifPresent(
                p -> {
                    System.out.println(p);
                    System.out.println(p.getDog());
                    System.out.println(System.identityHashCode(p));
                    System.out.println(System.identityHashCode(p.getDog()));
                }
        );
    }
}
