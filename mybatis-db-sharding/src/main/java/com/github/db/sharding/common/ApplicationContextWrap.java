package com.github.db.sharding.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 包装，直接赋值给静态变量，静态检查报错
 *
 * @author tangdingyi
 * @version 1.0 *
 * @since
 */
@Component
public class ApplicationContextWrap implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.setApplicationContext(applicationContext);
    }

}
