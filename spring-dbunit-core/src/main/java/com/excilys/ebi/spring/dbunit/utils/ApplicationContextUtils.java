/**
 * Copyright 2011-2012 eBusiness Information, Groupe Excilys (www.excilys.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
