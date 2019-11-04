package com.skyedu;

import java.util.Locale;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Spring工具
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2014-7-22
 */
@Component("springUtils")
@Lazy(false)

public final class SpringUtils implements DisposableBean,
		ApplicationContextAware {
	
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		context = applicationContext;
	}

	@Override
	public void destroy() {
		context = null;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public static Object getBean(String name) {
		Assert.hasText(name);
		return context.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> type) {
		Assert.hasText(name);
		Assert.notNull(type);
		return context.getBean(name, type);
	}

	/**
	 * 获得i18n字符串
	 * @param code 资源文件中的key
	 * @param args 对应资源文件中key值{0}{1}
	 * @return
	 */
	public static String getMessage(String code, Object[] args) {
		LocaleResolver localeResolver = (LocaleResolver) getBean("localeResolver", LocaleResolver.class);
		Locale locale = localeResolver.resolveLocale(null);
		return context.getMessage(code, args, locale);
	}
}