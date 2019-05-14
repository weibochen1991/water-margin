/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package mybatisgenerator;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 *  mybatis-generate 自定义插件，解决每次重新执行后，新的内容会以追加的方式加入到原来的xxxMapper.xml文件中。
 *  如果没有将isMergeable属性改成false 则每次执行只是在后面追加
 *
 * @author wb-cwb367549
 * @version $Id: OverIsMergeablePlugin.java, v 0.1 2019年03月25日 18:01 wb-cwb367549 Exp $
 */
public class OverIsMergeablePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            /**
             * 在IntrospectedTableMyBatis3Impl.getGeneratedXmlFiles方法中，isMergeable值被写死为true了。
             *需要修改成false
             */
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (Exception e) {
        }
        return true;
    }
}