package com.github.db.sharding.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * spring 应用上下文工具类
 *
 * @author tangdingyi
 * @version 1.0 *
 * @since
 */
public abstract class ApplicationContextUtils {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContextInter)
            throws BeansException {
        applicationContext = applicationContextInter;
    }

}
