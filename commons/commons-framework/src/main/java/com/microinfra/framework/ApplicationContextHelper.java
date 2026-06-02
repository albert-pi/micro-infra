package com.microinfra.framework;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public final class ApplicationContextHelper implements BeanFactoryPostProcessor, ApplicationContextAware {

	private static ConfigurableListableBeanFactory beanFactory;

	private static ApplicationContext applicationContext;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		ApplicationContextHelper.beanFactory = beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHelper.applicationContext = applicationContext;
	}

	public static String getApplicationName() {
		return applicationContext.getEnvironment().getProperty("spring.application.name");
	}

	public static ListableBeanFactory getBeanFactory() {
		return null == beanFactory ? applicationContext : beanFactory;
	}

	/**
	 * 获取名称为name的bean
	 * 
	 * @param <T>
	 * @param name
	 * @return
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) getBeanFactory().getBean(name);
	}

	/**
	 * 获取类型为T的bean
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(Class<T> clazz) throws BeansException {
		return getBeanFactory().getBean(clazz);
	}

	/**
	 * 获取类型为T的bean
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 * @throws BeansException
	 */
	public static <T> Map<String, T> getBeans(Class<T> clazz) throws BeansException {
		return getBeanFactory().getBeansOfType(clazz);
	}

	/**
	 * 检查BeanFactory是否包含一个名称为name的bean
	 *
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return getBeanFactory().containsBean(name);
	}

	/**
	 * 检查名称为name的bean是singleton还是prototype
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return getBeanFactory().isSingleton(name);
	}

	/**
	 * 获取名称为name的bean的类型
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return getBeanFactory().getType(name);
	}

	/**
	 * 获取名称为name的bean的所有别名
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return getBeanFactory().getAliases(name);
	}

	/**
	 * 获取AOP代理对象
	 * 
	 * @param <T>
	 * @param invoker
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAopProxy(T invoker) {
		return (T) AopContext.currentProxy();
	}

	/**
	 * 获取当前被激活的环境配置
	 * 
	 * @return
	 */
	public static String[] getActiveProfiles() {
		return applicationContext.getEnvironment().getActiveProfiles();
	}

	/**
	 * 获取当前被激活的环境配置中的第一个配置
	 * 
	 * @return
	 */
	public static String getActiveProfile() {
		final String[] activeProfiles = getActiveProfiles();

		return !ObjectUtils.isEmpty(activeProfiles) ? activeProfiles[0] : null;
	}

	/**
	 * 获取带有特定注解的bean
	 * 
	 * @param annotationClazz
	 * @return
	 */
	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationClazz) {
		return applicationContext != null ? applicationContext.getBeansWithAnnotation(annotationClazz) : null;
	}

}