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
/**
 *
 */
package com.excilys.ebi.spring.dbunit.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

import org.dbunit.DatabaseUnitException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.TestContext;

import com.excilys.ebi.spring.dbunit.config.DataSetConfiguration;

/**
 * Test configuration processor strategy to figure out if we need to load some
 * specific dataSet at Class or Method level.
 * 
 * <p>
 * Moreover, if in Method level, we just look for DataSet annotation on the
 * method (instead of TestConfigurationProcessor) because the Class should have
 * already been taken care of.
 * </p>
 * 
 * @author <a href="mailto:pcavezzan@gmail.com">Patrice CAVEZZAN</a>
 */
public class RollbackTransactionalTestConfigurationProcessor extends TestConfigurationProcessor {
	/**
	 * A configuration cache used between setup and teardown
	 */
	private final Map<Class<?>, DataSetConfiguration> configurationClassCache = Collections.synchronizedMap(new IdentityHashMap<Class<?>, DataSetConfiguration>());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.ebi.spring.dbunit.ConfigurationProcessor#getConfiguration
	 * (java.lang.Object)
	 */
	@Override
	public DataSetConfiguration getConfiguration(TestContext testContext) throws IOException, DatabaseUnitException {
		DataSetConfiguration configuration = null;

		final Method testMethod = testContext.getTestMethod();

		if (testMethod == null) { // beforeTestClass or afterTestClass

			configuration = configurationClassCache.get(testContext.getTestClass());

			if (configuration == null) {
				final DataSet dataSetAnnotation = AnnotationUtils.findAnnotation(testContext.getTestClass(), DataSet.class);

				if (dataSetAnnotation != null) {
					LOGGER.debug("Configuring database at Class level");
					configuration = buildConfiguration(dataSetAnnotation, testContext);
					configurationClassCache.put(testContext.getTestClass(), configuration);
				}
			}
		} else { // beforeTestMethod or afterTestMethod

			configuration = configurationCache.get(testMethod);
			if (configuration == null) {
				DataSet dataSetAnnotation = AnnotationUtils.findAnnotation(testContext.getTestMethod(), DataSet.class);

				if (dataSetAnnotation != null) {
					LOGGER.debug("Configuring database at Method level");
					configuration = buildConfiguration(dataSetAnnotation, testContext);
					configurationCache.put(testContext.getTestMethod(), configuration);
				} else if (configurationClassCache.get(testContext.getTestClass()) == null) {
					LOGGER.info("DataSetTestExecutionListener was configured but without any DataSet or DataSets! DataSet features are disabled");
				}
			}
		}

		return configuration;
	}

}
