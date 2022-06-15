package org.ryuji.ddd.demo.project.util;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

@Log4j2
public class SpringUtil {

    @Getter
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringUtil.applicationContext = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanId) {
        if (applicationContext == null) {
            return null;
        }
        if (beanId == null) {
            return null;
        }
        return (T) applicationContext.getBean(beanId);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        if (null == beanName || "".equals(beanName.trim())) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        return (T) applicationContext.getBean(beanName, clazz);
    }

    public static void publishEvent(ApplicationEvent event) {
        if (applicationContext == null) {
            return;
        }
        try {
            applicationContext.publishEvent(event);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

}
