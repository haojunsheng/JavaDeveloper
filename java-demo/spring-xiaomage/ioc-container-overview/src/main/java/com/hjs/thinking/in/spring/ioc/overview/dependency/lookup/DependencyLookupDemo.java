package com.hjs.thinking.in.spring.ioc.overview.dependency.lookup;

import com.hjs.thinking.in.spring.ioc.overview.annotation.Super;
import com.hjs.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找示例
 * 1. 通过名称的方式来查找
 *
 * @author 俊语
 * @date 2021/6/8 下午10:32
 */
public class DependencyLookupDemo {
    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");
        // 按照名称查找
        lookupByName(beanFactory);
        // 按照类型查找
        lookupByType(beanFactory);
        // 按照类型查找集合对象
        lookupCollectionByType(beanFactory);
        // 通过注解查找对象
        lookupByAnnotationType(beanFactory);
    }

    private static void lookupByName(BeanFactory beanFactory) {
        System.out.println("####名称查找开始####");
        lookupInRealTime(beanFactory);
        lookupInLazy(beanFactory);
        System.out.println("####名称查找结束####");
    }


    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        System.out.println("####注解查找开始####");
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找标注 @Super 所有的 User 集合对象：" + users);
        }
        System.out.println("####注解查找结束####");
    }

    private static void lookupCollectionByType(BeanFactory beanFactory) {
        System.out.println("####集合查找开始####");
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到的所有的 User 集合对象：" + users);
        }
        System.out.println("####集合查找结束####");
    }

    private static void lookupByType(BeanFactory beanFactory) {
        System.out.println("####类型查找开始####");
        User user = beanFactory.getBean(User.class);
        System.out.println("实时查找：" + user);
        System.out.println("####类型查找结束####");
    }

    /**
     * 延迟查找
     *
     * @param beanFactory
     */
    private static void lookupInLazy(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延迟查找：" + user);
    }

    private static void lookupInRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println("实时查找：" + user);
    }
}
