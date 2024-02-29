package com.slipper4j.framework.common.util.spring;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 手动注入
 *
 * @author zhougang
 * @since 2023/5/20 14:10
 */
public class SpringRegisterUtils {

    /**
     * 向IOC 容器中注入 bean
     *
     * @param clazz    bean class
     * @param beanName bean beanName
     */
    public static void registerBean(Class<?> clazz, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        //设置当前bean定义对象是单利的
        beanDefinition.setScope("singleton");
        if (!StringUtils.hasText(beanName)) {
            //将变量首字母置小写
            beanName = StringUtils.uncapitalize(clazz.getSimpleName());
            beanName = beanName.substring(beanName.lastIndexOf(".") + 1);
            beanName = StringUtils.uncapitalize(beanName);
        }

        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringUtil.getApplicationContext();
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 卸载bean
     *
     * @param className className
     * @throws Exception
     */
    public void unregisterBean(String className) throws Exception {

        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringUtil.getApplicationContext();
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        defaultListableBeanFactory.removeBeanDefinition(className);
    }

    /**
     * 注册Controller
     *
     * @param controllerBeanName controllerBeanName
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void registerController(String controllerBeanName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //先注册bean
        //registerBean(controllerBeanName);

        final RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean(RequestMappingHandlerMapping.class);
        Object controller = SpringUtil.getBean(controllerBeanName);
        //            unregisterController(controllerBeanName);
        if (controller == null) {
            return;
        }
        //注册Controller
        Method method = requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().
                getDeclaredMethod("detectHandlerMethods", Object.class);
        //将private改为可使用
        method.setAccessible(true);
        method.invoke(requestMappingHandlerMapping, controllerBeanName);
    }

    /**
     * 去掉Controller的Mapping
     *
     * @param controllerBeanName controllerBeanName
     */
    public static void unregisterController(String controllerBeanName) {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                SpringUtil.getBean("requestMappingHandlerMapping");
        if (requestMappingHandlerMapping != null) {
            Object controller = SpringUtil.getBean(controllerBeanName);
            if (controller == null) {
                return;
            }
            final Class<?> targetClass = controller.getClass();
            ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
                public void doWith(Method method) {
                    Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
                    try {
                        Method createMappingMethod = RequestMappingHandlerMapping.class.
                                getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
                        createMappingMethod.setAccessible(true);
                        RequestMappingInfo requestMappingInfo = (RequestMappingInfo)
                                createMappingMethod.invoke(requestMappingHandlerMapping, specificMethod, targetClass);
                        if (requestMappingInfo != null) {
                            requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
    }
}
