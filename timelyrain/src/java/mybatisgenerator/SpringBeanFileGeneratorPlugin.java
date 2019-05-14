/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package mybatisgenerator;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wb-cwb367549
 * @version $Id: SpringBeanFileGeneratorPlugin.java, v 0.1 2019年05月14日 14:19 wb-cwb367549 Exp $
 */
public class SpringBeanFileGeneratorPlugin extends PluginAdapter {

  private List<IntrospectedTable> configs;

    public SpringBeanFileGeneratorPlugin() {
        this.configs = new ArrayList<>();
    }

    /**
     *  根据自己需要生成xml文件
     * @return
     */
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
        Document document = new Document();

        XmlElement root = new XmlElement("beans");


/*
        root.addAttribute(new Attribute("xmlns", "http://www.springframework.org/schema/beans"));
        root.addAttribute(new Attribute("xmlns:sofa", "http://schema.alipay.com/sofa/schema/service"));
        root.addAttribute(new Attribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance"));
        root.addAttribute(new Attribute("xsi:schemaLocation", "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd"));

        document.setRootElement(root);

        for (IntrospectedTable config :this.configs){
            XmlElement beanEL = new XmlElement("bean");
            beanEL.addAttribute(new Attribute("id", getId(config.getDAOInterfaceType())));
            beanEL.addAttribute(new Attribute("class", config.getDAOImplementationType()));

            root.addElement(beanEL);
        }*/
        GeneratedXmlFile gxf = new GeneratedXmlFile(document, this.properties
                .getProperty("fileName", "SqlMapConfig.xml"),
                this.properties
                        .getProperty("targetPackage"),
                this.properties
                        .getProperty("targetProject"),
                false, this.context
                .getXmlFormatter());
        List answer = new ArrayList(1);
        answer.add(gxf);
        return answer;
    }

    /**
     * 获取id 类名首字母小写
     * @param classFuleName
     * @return
     */
    private String getId(String classFuleName){
        StringBuffer id = new StringBuffer();
        id.append(classFuleName.substring(classFuleName.lastIndexOf(".") + 1));
        id.setCharAt(0,Character.toLowerCase(id.charAt(0)));
        return id.toString();
    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    public static void main(String[] args) {
        String classFuleName = "com.weibo.Main";
        StringBuffer id = new StringBuffer();
        id.append(classFuleName.substring(classFuleName.lastIndexOf(".") + 1));
       // id.charAt(0);
        id.setCharAt(0,Character.toLowerCase(id.charAt(0)));
        System.out.println(id.toString());
    }

}