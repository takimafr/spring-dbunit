package com.excilys.ebi.spring.dbunit.utils;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

public class ApplicationContextUtils {

	private ApplicationContextUtils() {
		throw new UnsupportedOperationException();
	}

	public static <T> T getOptionalUniqueBeanOfType(ApplicationContext applicationContext, Class<T> type) {
		Map<String, T> configs = applicationContext.getBeansOfType(type);

		Assert.isTrue(configs.size() <= 1, "found more than one bean in the applicationContext");

		return configs.size() == 1 ? configs.values().iterator().next() : null;
	}

	public static <T> T getOptionalUniqueBeanOfType(ApplicationContext applicationContext, Class<T> type, T defaultValue) {

		T bean = getOptionalUniqueBeanOfType(applicationContext, type);
		return bean != null ? bean : defaultValue;
	}
}
